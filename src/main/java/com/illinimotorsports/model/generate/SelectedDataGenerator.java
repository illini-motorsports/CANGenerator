package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANMessage;

import java.util.List;

public abstract class SelectedDataGenerator implements CodeGenerator{

    List<CANMessage> data;

    public void setData(List<CANMessage> data) {
        this.data = data;
    }
}
