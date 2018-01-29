package com.illinimotorsports.model.generate;

import com.illinimotorsports.view.CheckBoxView;

import java.util.List;

public abstract class SelectedDataGenerator implements CodeGenerator {

    private List<CheckBoxView> data;

    protected List<CheckBoxView> getData() {
        return data;
    }

    public void setData(List<CheckBoxView> data) {
        this.data = data;
    }
}
