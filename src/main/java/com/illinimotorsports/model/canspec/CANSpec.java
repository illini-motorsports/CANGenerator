package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CANSpec {
  private String version;
  private List<CANMessage> messages;

  public CANSpec(String version) {
    this.version = version;
    this.messages = new ArrayList<>();
  }

  public void addMessage(CANMessage message) {
    messages.add(message);
  }

  public List<String> getMessages() {
    List<String> messagesList = new ArrayList<>();
    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      CANMessage message = (CANMessage) messagesIter.next();
      messagesList.add(message.getNode() + message.getId());
    }
    return messagesList;
  }

  public List<List<String>> getMessagesWithFields() {
    List<List<String>> messagesList = new ArrayList<>();
    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      CANMessage message = (CANMessage) messagesIter.next();
      List<String> fields = new ArrayList<>();
      fields.add(message.getNode() + ": 0x" + Integer.toHexString(message.getId()));
      fields.addAll(message.getFieldNames());
      messagesList.add(fields);
    }
    return messagesList;
  }
}
