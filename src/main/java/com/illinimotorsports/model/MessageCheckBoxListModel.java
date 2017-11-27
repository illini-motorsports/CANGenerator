package com.illinimotorsports.model;

import com.illinimotorsports.view.MessageCheckBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MessageCheckBoxListModel extends AbstractListModel<MessageCheckBox> {

  private List<MessageCheckBox> data;

  public MessageCheckBoxListModel(List<List<String>> messageList) {
    super();
    data = new ArrayList<>();
    for(List<String> message: messageList) {
      data.add(new MessageCheckBox(message.get(0), message.subList(1, message.size())));
    }
  }

  @Override
  public int getSize() {
    return data.size();
  }

  @Override
  public MessageCheckBox getElementAt(int index) {
    return data.get(index);
  }
}
