package com.illinimotorsports.model.generate;

import com.illinimotorsports.model.canspec.CANMessage;

import java.util.List;

public abstract class SelectedMessagesGenerator implements TemplatedGenerator {

  List<CANMessage> messages = null;

  public void setMessages(List<CANMessage> canMessages) {
    messages = canMessages;
  }

  public abstract String fillTemplate();
}
