package com.illinimotorsports.controller;

import com.illinimotorsports.model.SelectModel;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.generate.SelectedMessagesGenerator;
import com.illinimotorsports.view.CheckBoxListView;
import com.illinimotorsports.view.SelectView;

import java.util.ArrayList;
import java.util.Set;

public class SelectController {

  private SelectView view;
  private SelectModel model;

  public SelectController(CANSpec spec) {
    model = new SelectModel();
    view = new SelectView(new CheckBoxListView(model));
  }

  public SelectController(CANSpec spec, Set<Integer> ids) {
    model = new SelectModel();
    model.setData(new ArrayList<>(ids));
    view = new SelectView(new CheckBoxListView(model));
  }

  public void init(SelectedMessagesGenerator generator) {
    // Add action listeners
    view.getSelectAllButton().addActionListener(e -> view.getList().setAll(true));
    view.getDeselectAllButton().addActionListener(e -> view.getList().setAll(false));
    view.getSubmitButton().addActionListener(e -> doneListener(generator));

    view.init();
  }

  public void init() {
    // Add action listeners
    view.getSelectAllButton().addActionListener(e -> view.getList().setAll(true));
    view.getDeselectAllButton().addActionListener(e -> view.getList().setAll(false));

    view.init();
  }

  public void doneListener(SelectedMessagesGenerator generator) {
    view.setVisible(false);
    // Figure out what checkboxes were selected, and pass that to the generator
    generator.setMessages(model.getSelectedMessages());

    GeneratedCodeController genCode = new GeneratedCodeController(generator);
    genCode.init();
  }
}
