package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.view.MessageCheckBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MessageCheckBoxListModel extends AbstractListModel<MessageCheckBox> {

  private List<MessageCheckBox> data;

  public MessageCheckBoxListModel(CANSpec spec) {
    super();
    data = new ArrayList<>();
    for(CANMessage message: spec.getMessages()) {
      data.add(new MessageCheckBox(message));
    }
  }

  public void setAll(boolean selected) {
    for(MessageCheckBox box: data) {
      box.getCheckBox().setSelected(selected);
    }
  }

  public List<CANMessage> getSelectedMessages() {
    List<CANMessage> messages = new ArrayList<>();
    for(MessageCheckBox box: data) {
      if(box.getCheckBox().isSelected()) {
        messages.add(box.getMessage());
      }
    }
    return messages;
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
