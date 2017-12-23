package com.illinimotorsports.controller;

import com.illinimotorsports.model.MessageSelectModel;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.generate.SelectedMessagesGenerator;
import com.illinimotorsports.view.MessageCheckBoxListView;
import com.illinimotorsports.view.MessageSelectView;

public class MessageSelectController {

  private MessageSelectView view;
  private MessageSelectModel model;

  public MessageSelectController(CANSpec spec) {
    model = new MessageSelectModel(spec);
    view = new MessageSelectView(new MessageCheckBoxListView(model));
  }

  public void init(SelectedMessagesGenerator generator) {
    // Add action listeners
    view.getSelectAllButton().addActionListener(e -> view.getList().setAll(true));
    view.getDeselectAllButton().addActionListener(e -> view.getList().setAll(false));
    view.getSubmitButton().addActionListener(e -> doneListener(generator));

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
