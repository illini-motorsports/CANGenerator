package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANBitmapField;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldCheckBoxView extends CheckBoxView {

    private CANDataField field;

    public FieldCheckBoxView(CANDataField field) {
        super();
        this.field = field;
        this.setCheckBoxText(field.getNode() + " " + field.getName());
        String bitText = "";
        if(field instanceof CANBitmapField) {
            // TODO: make this less shitty
            CANBitmapField bitmapField = (CANBitmapField) field;
            for (int i = 0; i < bitmapField.getBits().size() - 1; i++) {
                if (i % 4 == 0 && i != 0) {
                    bitText += "\n";
                }
                bitText += "  " + bitmapField.getBits().get(i).getName() + ",";
            }
            bitText += "  " + bitmapField.getBits().get(bitmapField.getBits().size() - 1).getName();
        }
        this.setTextArea(bitText);
    }

    public Object getData() {
        return field;
    }

    public static List<CheckBoxView> generateCheckBoxViews(CANSpec spec, Set<Integer> ids) {
        return spec.getMessages().stream().filter(x -> ids.contains(x.getId()))
                .flatMap(x -> x.getData().stream()).map(x -> new FieldCheckBoxView(x))
                .collect(Collectors.toList());
    }

    public static List<CheckBoxView> generateCheckBoxViews(CANSpec spec) {
        return spec.getMessages().stream().flatMap(x -> x.getData().stream()).map(x -> new FieldCheckBoxView(x))
                .collect(Collectors.toList());
    }
}
