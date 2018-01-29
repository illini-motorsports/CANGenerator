package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageCheckBoxView extends CheckBoxView {

    private CANMessage message;

    public MessageCheckBoxView(CANMessage message) {
        super();
        this.message = message;
        setCheckBoxText(message.getNode() + " 0x" + Integer.toHexString(message.getId()));
        String fieldText = "Fields:\n";
        List<String> fieldNames = message.getFieldNames();
        for(int i = 0; i < fieldNames.size() - 1; i++) {
            if(i % 4 == 0 && i != 0) {
                fieldText += "\n";
            }
            fieldText += "  " + fieldNames.get(i) + ",";
        }
        fieldText += "  " + fieldNames.get(fieldNames.size()-1);
        setTextArea(fieldText);
    }

    public Object getData() {
        return message;
    }

    public static List<CheckBoxView> generateCheckBoxViews(CANSpec spec) {
        return spec.getMessages().stream().map(x -> new MessageCheckBoxView(x))
                .collect(Collectors.toList());
    }

    public static List<MessageCheckBoxView> generateCheckBoxViews(CANSpec spec, Set<Integer> ids) {
        return spec.getMessages().stream().filter(x -> ids.contains(x.getId()))
                .map(x -> new MessageCheckBoxView(x)).collect(Collectors.toList());
    }
}
