package com.illinimotorsports.controller;

import com.illinimotorsports.model.SelectModel;
import com.illinimotorsports.model.generate.CodeGenerator;
import com.illinimotorsports.model.generate.SelectedDataGenerator;
import com.illinimotorsports.view.CheckBoxListView;
import com.illinimotorsports.view.SelectView;

import java.util.ArrayList;
import java.util.Collection;

public class SelectController {

  private SelectView view;
  private SelectModel model;

  public SelectController(Collection data) {
    model = new SelectModel();
    model.setData(new ArrayList<>(data));
    view = new SelectView(new CheckBoxListView(model));
  }

  public void init(SelectedDataGenerator generator) {
    view.getSubmitButton().addActionListener(e -> doneListener(generator));
    init();
  }

  public void init() {
    // Add action listeners
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
