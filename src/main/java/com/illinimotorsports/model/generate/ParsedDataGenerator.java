package com.illinimotorsports.model.generate;

import com.illinimotorsports.controller.DocumentationController;
import com.illinimotorsports.model.DocumentationTableModel;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.parse.CANLoggedMessage;
import com.illinimotorsports.model.parse.LoggedMessages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParsedDataGenerator extends SelectedDataGenerator {

    private File[] filesToParse;
    private String[] fieldNames;
    private List<String[]> parseData;
    private CANSpec canSpec;

    public ParsedDataGenerator(CANSpec spec, File[] files, String[] fields,
                               List<String[]> data) {
        canSpec = spec;
        filesToParse = files;
        fieldNames = fields;
        parseData = data;
    }

    @Override
    public String generate() {
        //TODO: Generate CSV and return as string

        String[] columnNames = new String[getData().stream().map(x -> (CANDataField) x.getData())
                .collect
                (Collectors.toList()).size() + 1];
        columnNames[0] = "Timestamp";
        for (int i = 1; i < columnNames.length; i++) {
            for (int j = 0; j < parseData.size(); j++) {
                if (getData().stream().map(x -> (CANDataField) x.getData())
                        .collect
                                (Collectors.toList()).get(i - 1).getName() == parseData.get(j)[0]) {
                    columnNames[i] = getData().stream().map(x -> (CANDataField) x.getData())
                            .collect
                                    (Collectors.toList()).get(i - 1).getName() + "(" + parseData.get(j)
                            [1] + ")";
                }
            }
        }
        List<String[]> rows = new ArrayList<>();

        for (CANLoggedMessage message: LoggedMessages.parseFileList(filesToParse)) {
            String[] row = new String[getData().stream().map(x -> (CANDataField) x.getData()).collect
                    (Collectors.toList()).size() + 1];
            int index = 0;
            row[index] = Double.toString(message.getTimestamp());
            index++;
            for(CANDataField dataField: getData().stream().map(x -> (CANDataField) x.getData()).collect
                    (Collectors.toList())) {
                boolean valFound = false;
                for (int i = 0; i < canSpec.getMessages().size(); i++) {
                    for (String fieldName: canSpec.getMessages().get(i).getFieldNames()) {
                        if (dataField.getName() == fieldName) {
                            for (int j = 0; j < parseData.size(); j++) {
                                if (dataField.getName() == parseData.get(j)[0]) {
                                    if (message.getId() == canSpec.getMessages().get(i).getId()) {
                                        boolean signed = true;
                                        if (parseData.get(j)[4].toLowerCase().equals("unsigned")) {
                                            signed = false;
                                        }
                                        row[index] = Double.toString(message.getNumericField(
                                                dataField.getPosition(), dataField.getLength(),
                                                Double.parseDouble(parseData.get(j)[2]),
                                                Integer.parseInt(parseData.get(j)[3]
                                                        .substring(2), 16),
                                                signed, canSpec.getMessages().get(i)
                                                        .getEndianness()));
                                        valFound = true;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!valFound) {
                    row[index] = Double.toString(0);
                }
                index++;
            }
            rows.add(row);
        }

        DocumentationTableModel model = new DocumentationTableModel(columnNames, rows);
        DocumentationController controller = new DocumentationController(model);
        controller.init(false);

        return "";
    }
}