package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: Move more functionality into this class
public class FieldCheckBoxView extends CheckBoxView {
    public FieldCheckBoxView(CANMessage message, CANDataField field) {
        super(message, field);
    }

    public FieldCheckBoxView(CANDataField field) {
        // TODO: handle this properly
        super(null, field);
    }

    public static List<FieldCheckBoxView> generateCheckBoxViews(CANSpec spec, Set<Integer> ids) {
        return spec.getMessages().stream().filter(x -> ids.contains(x.getId()))
                .flatMap(x -> x.getData().stream()).map(x -> new FieldCheckBoxView(x))
                .collect(Collectors.toList());
    }
}
