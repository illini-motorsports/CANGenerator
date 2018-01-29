package com.illinimotorsports.controller;

import com.illinimotorsports.model.SelectModel;
import com.illinimotorsports.model.generate.SelectedDataGenerator;
import com.illinimotorsports.view.CheckBoxListView;
import com.illinimotorsports.view.CheckBoxView;
import com.illinimotorsports.view.SelectView;

import java.util.List;

public class SelectController {

  private SelectView view;
  private SelectModel model;

  public SelectController(List<CheckBoxView> data) {
    model = new SelectModel();
    model.setData(data);
    view = new SelectView(new CheckBoxListView(model));
  }

  public void init(SelectedDataGenerator generator) {
    // Add action listener
    view.getSubmitButton().addActionListener(e -> doneListener(generator));
    view.getSelectAllButton().addActionListener(e -> view.getList().setAll(true));
    view.getDeselectAllButton().addActionListener(e -> view.getList().setAll(false));

    view.init();
  }

  public void doneListener(SelectedDataGenerator generator) {
    view.setVisible(false);
    // Figure out what checkboxes were selected, and pass that to the generator
    generator.setData(model.getSelectedMessages());

    GeneratedCodeController genCode = new GeneratedCodeController(generator);
    genCode.init();
  }
}
