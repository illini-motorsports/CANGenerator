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

    /*
        This function is responsible for generating a CSV from CANDataFields and CANLoggedMessages
        It makes heavy use of streams, which allows for more parallelization in the CSV generation,
        as the amount of data that must be parsed can be very large
     */
    @Override
    public String generate() {
        // Create list of selected fields
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
        table.rowKeySet().stream().forEach(timestamp ->  {
            String[] row = new String[numFields + 1];
            row[0] = df.format(timestamp);
            Map<String, Double> tableRow = table.row(timestamp);
            for(int i = 0; i < numFields; i++) {
                String name = columnNames.get(i);

                row[i+1] = tableRow.containsKey(name) ? df.format(tableRow.get(name)) : "";
            }
            outputBuilder.append(String.join(",", row) + '\n');
        });

        return outputBuilder.toString();
    }
}