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
    private final int COLUMN_OFFSET = 1896;
    private final int MAX_LINE_LENGTH = 400;
    private final int NUM_CHUNKS = 2;

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

        long start = System.nanoTime();

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
                System.out.println("Message Timestamp: " + message.getTimestamp());
                table.put(message.getTimestamp(), field.getName(), message.getField(field));
            });
        });

        DecimalFormat df = new DecimalFormat("#.0000");
        int numFields = columnNames.size();

        System.out.println("Setup Time: " + ((System.nanoTime() - start) / 1000 / 1000 / 1000) +
                " seconds");

        /*   CHUNKED IMPLEMENTATION    */
        // put all keys (timestamps) in list
        System.out.println(table.rowKeySet());
        ArrayList<Double> rowKeyList = new ArrayList<>(table.rowKeySet());
        // list of key (timestamp) lists
        ArrayList<ArrayList<Double>> rowKeyChunked = new ArrayList<>();
        int CHUNK_SIZE = rowKeyList.size() / NUM_CHUNKS;
        System.out.println("Chunk Size: " + CHUNK_SIZE);
        // split data (rows) into chunks
        Map<Double, Integer> timestampToIndexMap = new HashMap<>();
        for (int i = 0; i < NUM_CHUNKS; i++) {
            // store chunks in rowKeyChunked
            System.out.println("Lower Bound: " + CHUNK_SIZE * i);
            System.out.println("Upper Bound: " + (CHUNK_SIZE * (i + 1) - 1));
            rowKeyChunked.add(sublist(rowKeyList, CHUNK_SIZE * i,
                    (CHUNK_SIZE * (i + 1) - 1) + 1));
            // associate first timestamp of chunk with chunk index
            timestampToIndexMap.put(Double.parseDouble(df.format(rowKeyList.get(CHUNK_SIZE * i)))
                    , i);
        }
        for (int i = 0; i < NUM_CHUNKS; i++) {
            for (int j = 0; j < rowKeyChunked.get(i).size(); j++) {
                if (timestampToIndexMap.get(rowKeyChunked.get(i).get(j)) != null) {
                    System.out.println("Index: " + timestampToIndexMap.get(Double.parseDouble(df.format
                            (rowKeyChunked.get(i).get(j)))));
                }
                System.out.println( "Timestamp: "  + rowKeyChunked.get(i).get(j));
            }
        }
        // build chunk stringbuilders in parallel
        // this stringbuilder gets way too big
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Timestamp,");
        outputBuilder.append(String.join(",", columnNames) + '\n');
        int COLUMN_TITLE_LENGTH = outputBuilder.length();
        for (int i = 0; i < table.rowKeySet().size(); i++) {
            for (int j = 0; j < MAX_LINE_LENGTH; j++) {
                outputBuilder.append("*");
            }
        }
        System.out.println("Size of Output: " + outputBuilder.length() + " bytes");
        rowKeyChunked.parallelStream().forEach(x -> {
            System.out.println("Parallel Stream Chunk Size: " + x.size());
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
            System.out.println("Chunk String Builder");
            System.out.println(chunkStringBuilder.toString());
            Double chunkFirstTimestamp = Double.parseDouble(chunkStringBuilder.toString().split(",")[0]);
            System.out.println("Chunk First Timestamp: " + chunkFirstTimestamp);
            int chunkIndex = timestampToIndexMap.get(chunkFirstTimestamp);
            System.out.println("Chunk Index: " + chunkIndex);
            outputBuilder.replace(COLUMN_TITLE_LENGTH + chunkIndex * CHUNK_SIZE * MAX_LINE_LENGTH,
                    COLUMN_TITLE_LENGTH + (chunkIndex + 1) *
                    CHUNK_SIZE * MAX_LINE_LENGTH, chunkStringBuilder.toString());
        });

        return outputBuilder.toString();
    }
}