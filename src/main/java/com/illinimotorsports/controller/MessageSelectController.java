package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratedCodeModel;
import com.illinimotorsports.model.MessageSelectModel;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.model.generate.SelectedMessagesGenerator;

import com.illinimotorsports.view.GeneratedCodeView;
import com.illinimotorsports.view.MessageCheckBoxListView;
import com.illinimotorsports.view.MessageSelectView;

import java.util.List;

public class MessageSelectController {

  private MessageSelectView view;
  private MessageSelectModel model;

  public MessageSelectController(CANSpec spec) {
    model = new MessageSelectModel(spec);
    view = new MessageSelectView(new MessageCheckBoxListView(model));
  }

  public void init(SelectedMessagesGenerator generator) {
    view.getSelectAllButton().addActionListener(e -> view.getList().setAll(true));
    view.getDeselectAllButton().addActionListener(e -> view.getList().setAll(false));
    view.getSubmitButton().addActionListener(e -> doneListener(generator));
    view.init();
  }

  public void doneListener(SelectedMessagesGenerator generator) {
    view.setVisible(false);
    List<CANMessage> messages = model.getSelectedMessages();
    generator.setMessages(messages);
    GeneratedCodeController genCode = new GeneratedCodeController(
        new GeneratedCodeModel(generator),
        new GeneratedCodeView()
    );
    genCode.init();
  }
}
