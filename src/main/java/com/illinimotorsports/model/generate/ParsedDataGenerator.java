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

    @Override
    public String generate() {
        List<CANDataField> selectedFields = getData().stream().map(x -> (CANDataField) x.getData()).collect(Collectors.toList());

        Set<Integer> selectedIDs = selectedFields.stream().map(x -> x.getNodeId()).collect(Collectors.toSet());

        List<String> columnNames = selectedFields.stream().map(x -> x.getName()).collect(Collectors.toList());

        ImmutableMultimap.Builder<Integer, CANDataField> builder = new ImmutableSetMultimap.Builder<>();
        selectedFields.stream().forEach(x -> builder.put(x.getNodeId(), x));
        Multimap<Integer, CANDataField> fieldMultimap = builder.build();

        List<CANLoggedMessage> filteredMessages = messages.getMessages().stream()
                .filter(x -> selectedIDs.contains(x.getId())).collect(Collectors.toList());

        SortedMap<Double, Map<String, Double>> table = new TreeMap<>();
        for(CANLoggedMessage message: filteredMessages) {
            for(CANDataField field: fieldMultimap.get(message.getId())) {
                double fieldValue = message.getField(field);
                double timestamp = message.getTimestamp();
                if(!table.containsKey(timestamp)) {
                    table.put(timestamp, new HashMap<>());
                }
                table.get(timestamp).put(field.getName(), fieldValue);
            }
        }

        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Timestamp,");
        outputBuilder.append(String.join(",", columnNames) + '\n');
        DecimalFormat df = new DecimalFormat("#.0000");
        int numFields = columnNames.size();
        for(SortedMap.Entry<Double, Map<String, Double>> entry: table.entrySet()) {
            String[] row = new String[numFields + 1];
            row[0] = df.format(entry.getKey());
            for(int i = 0; i < numFields; i++) {
                String name = columnNames.get(i);
                row[i+1] = entry.getValue().containsKey(name) ? df.format(entry.getValue().get(name)) : "";
            }
            outputBuilder.append(String.join(",", row) + '\n');
        }

        return outputBuilder.toString();
    }
}