package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageCheckBoxView extends CheckBoxView {
    public MessageCheckBoxView(CANMessage message) {
        super(message);
    }

    public static List<MessageCheckBoxView> generateCheckBoxViews(CANSpec spec) {
        return spec.getMessages().stream().map(x -> new MessageCheckBoxView(x))
                .collect(Collectors.toList());
    }

    public static List<MessageCheckBoxView> generateCheckBoxViews(CANSpec spec, Set<Integer> ids) {
        return spec.getMessages().stream().filter(x -> ids.contains(x.getId()))
                .map(x -> new MessageCheckBoxView(x)).collect(Collectors.toList());
    }
}
