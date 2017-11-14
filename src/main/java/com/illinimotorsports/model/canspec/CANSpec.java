package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
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
    for(int i = 0; i < messages.size(); i ++) {
      messagesList.add(messages.get(i).getNode() + messages.get(i).getId());
    }
    return messagesList;
  }
}
