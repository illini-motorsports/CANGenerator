package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.view.MessageCheckBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for checkbox list
 * Has special functions for getting selected messages and setting all boxes
 */
public class MessageCheckBoxListModel extends AbstractListModel<MessageCheckBox> {

  private List<MessageCheckBox> data;

  /**
   * Sets up internal data structure
   * @param spec
   */
  public MessageCheckBoxListModel(CANSpec spec) {
    super();
    data = new ArrayList<>();
    for(CANMessage message: spec.getMessages()) {
      data.add(new MessageCheckBox(message));
    }
  }

  /**
   * Sets or unsets all checkboxes
   * @param selected
   */
  public void setAll(boolean selected) {
    for(MessageCheckBox box: data) {
      box.getCheckBox().setSelected(selected);
    }
  }

  /**
   * Returns a list of CANMessages that were selected
   * @return
   */
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
