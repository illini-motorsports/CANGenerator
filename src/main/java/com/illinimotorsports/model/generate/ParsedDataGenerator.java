package com.illinimotorsports.model.generate;

import com.google.common.collect.*;
import com.illinimotorsports.controller.DocumentationController;
import com.illinimotorsports.model.DocumentationTableModel;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANNumericField;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANLoggedMessage;
import com.illinimotorsports.model.parse.LoggedMessages;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParsedDataGenerator extends SelectedDataGenerator {

    private LoggedMessages messages;

    public ParsedDataGenerator(LoggedMessages messages) {
        this.messages = messages;
    }

    private ArrayList<Double> sublist(List<Double> fullList, int lowerBound, int higherBound) {
        ArrayList<Double> sub = new ArrayList<>();
        for (int i = lowerBound; i < higherBound; i++) {
            sub.add(fullList.get(i));
        }
        return sub;
    }

    /*
        This function is responsible for generating a CSV from CANDataFields and CANLoggedMessages
        It makes heavy use of streams, which allows for more parallelization in the CSV generation,
        as the amount of data that must be parsed can be very large
     */
    @Override
    public String generate() {

        List<CANDataField> selectedFields = getData().stream().map(x -> (CANDataField) x.getData()).collect(Collectors.toList());

        // Create sets and lists of relevant fields from the selected fields
        Set<Integer> selectedIDs = selectedFields.stream().map(x -> x.getNodeId()).collect(Collectors.toSet());
        List<String> columnNames = selectedFields.stream().map(x -> x.getName()).collect(Collectors.toList());

        // Gather all fields from each message
        ImmutableMultimap.Builder<Integer, CANDataField> builder = new ImmutableSetMultimap.Builder<>();
        selectedFields.stream().forEach(x -> builder.put(x.getNodeId(), x));
        Multimap<Integer, CANDataField> fieldMultimap = builder.build();

        Table<Double, String, Double> table = TreeBasedTable.create();
        messages.getMessages().stream().filter(x -> selectedIDs.contains(x.getId())).forEach(message -> {
            fieldMultimap.get(message.getId()).stream().forEach(field -> {
                table.put(message.getTimestamp(), field.getName(), message.getField(field));
            });
        });

        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Timestamp,");
        outputBuilder.append(String.join(",", columnNames) + '\n');
        DecimalFormat df = new DecimalFormat("#.0000");
        int numFields = columnNames.size();

        /*   CHUNKED IMPLEMENTATION    */
        ArrayList<Double> rowKeyList = new ArrayList<>(table.rowKeySet());
        ArrayList<ArrayList<Double>> rowKeyChunked = new ArrayList<>();
        // split data (rows) into chunks
        final int NUM_CHUNKS = 10000;
        for (int i = 0; i < NUM_CHUNKS; i++) {
            rowKeyChunked.add(sublist(rowKeyList, i * (rowKeyList.size() / NUM_CHUNKS), (i + 1) *
                    (rowKeyList.size() / NUM_CHUNKS) - 1));
        }
        // build chunk stringbuilders in parallel
        Set<StringBuilder> chunkStringBuilders = new HashSet<>();
        long start = System.nanoTime();
        rowKeyChunked.parallelStream().forEach(x -> {
            StringBuilder chunkStringBuilder = new StringBuilder();
            x.stream().forEach(timestamp -> {
                String[] row = new String[numFields + 1];
                row[0] = df.format(timestamp);
                Map<String, Double> tableRow = table.row(timestamp);
                for(int i = 0; i < numFields; i++) {
                    String name = columnNames.get(i);

                    row[i+1] = tableRow.containsKey(name) ? df.format(tableRow.get(name)) : "";
                }
                chunkStringBuilder.append(String.join(",", row) + '\n');
            });
            chunkStringBuilders.add(chunkStringBuilder);
        });
        System.out.println(System.nanoTime() - start);

        // putting back together needs work
        start = System.nanoTime();
        ArrayList<StringBuilder> chunkStringList = new ArrayList<>(chunkStringBuilders);

        // sort based in order of first timestamp, earliest to latest timestamp

        chunkStringList.sort((a, b) -> {
            return Double.compare(Double.parseDouble(a.toString().split(",")[0]), Double
                    .parseDouble(b.toString().split(",")[0]));
        });

        outputBuilder.append(chunkStringList.stream().reduce(new StringBuilder(), (a, b) -> a
                .append(b)));

        /*                  */
        System.out.println(System.nanoTime() - start);
        return outputBuilder.toString();

        // ORIGINAL

//        List<CANDataField> selectedFields = getData().stream().map(x -> (CANDataField) x.getData()).collect(Collectors.toList());
//
//        // Create sets and lists of relevant fields from the selected fields
//        Set<Integer> selectedIDs = selectedFields.stream().map(x -> x.getNodeId()).collect(Collectors.toSet());
//        List<String> columnNames = selectedFields.stream().map(x -> x.getName()).collect(Collectors.toList());
//
//        // Gather all fields from each message
//        ImmutableMultimap.Builder<Integer, CANDataField> builder = new ImmutableSetMultimap.Builder<>();
//        selectedFields.stream().forEach(x -> builder.put(x.getNodeId(), x));
//        Multimap<Integer, CANDataField> fieldMultimap = builder.build();
//
//        Table<Double, String, Double> table = TreeBasedTable.create();
//        messages.getMessages().stream().filter(x -> selectedIDs.contains(x.getId())).forEach(message -> {
//            fieldMultimap.get(message.getId()).stream().forEach(field -> {
//                table.put(message.getTimestamp(), field.getName(), message.getField(field));
//            });
//        });
//
//        StringBuilder outputBuilder = new StringBuilder();
//        outputBuilder.append("Timestamp,");
//        outputBuilder.append(String.join(",", columnNames) + '\n');
//        DecimalFormat df = new DecimalFormat("#.0000");
//        int numFields = columnNames.size();
//        table.rowKeySet().stream().forEach(timestamp ->  {
//            String[] row = new String[numFields + 1];
//            row[0] = df.format(timestamp);
//            Map<String, Double> tableRow = table.row(timestamp);
//            for(int i = 0; i < numFields; i++) {
//                String name = columnNames.get(i);
//
//                row[i+1] = tableRow.containsKey(name) ? df.format(tableRow.get(name)) : "";
//            }
//            outputBuilder.append(String.join(",", row) + '\n');
//        });
//        return outputBuilder.toString();
    }
}