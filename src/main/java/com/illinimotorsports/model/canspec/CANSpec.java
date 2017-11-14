package com.illinimotorsports.model.canspec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Top level CAN spec class
 * Contains list of CAN messages
 */
public class CANSpec {
  private String version;
  private List<CANMessage> messages;

  /**
   * Constructor with all primitive fields
   * @param version
   */
  public CANSpec(String version) {
    this.version = version;
    this.messages = new ArrayList<>();
  }

  /**
   * Add message so the spec can be built up incrementally
   * @param message
   */
  public void addMessage(CANMessage message) {
    messages.add(message);
  }

  /**
   * Simple getter for all message names
   * @return
   */
  public List<String> getMessages() {
    List<String> messagesList = new ArrayList<>();
    Iterator messagesIter = messages.iterator();
    while(messagesIter.hasNext()) {
      CANMessage message = (CANMessage) messagesIter.next();
      messagesList.add(message.getNode() + ": 0x" + Integer.toHexString(message.getId()));
    }
    return messagesList;
  }

  /**
   * Returns a list of list of strings containing
   * all message names and field names.  First element of each inner
   * list will be the message name, and subsequent ones will be field names
   * @return
   */
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
