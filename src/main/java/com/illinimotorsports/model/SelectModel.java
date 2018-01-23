package com.illinimotorsports.model;

import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.canspec.CANSpec;
import com.illinimotorsports.view.CheckBoxView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Model for checkbox list
 * Has special functions for getting selected messages and setting all boxes
 */
public class SelectModel<T extends CheckBoxView> extends AbstractListModel<T> {

  private List<T> data;

  /**
   * Sets up internal data structure
   * @param spec
   */
  public SelectModel() {
    super();
    data = new ArrayList<>();
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  /**
   * Sets or unsets all checkboxes
   * @param selected
   */
  public void setAll(boolean selected) {
    for(T box: data) {
      box.getCheckBox().setSelected(selected);
    }
  }

  /**
   * Returns a list of CANMessages that were selected
   * @return
   */
  public List<CANMessage> getSelectedMessages() {
    List<CANMessage> messages = new ArrayList<>();
    for(T box: data) {
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
  public T getElementAt(int index) {
    return data.get(index);
  }

  public static List<CheckBoxView> getMessageList(CANSpec spec) {
    List<CheckBoxView> data = new ArrayList<>();
    for(CANMessage message: spec.getMessages()) {
      data.add(new CheckBoxView(message));
    }
    return data;
  }

  public static List<CheckBoxView> getFieldList(CANSpec spec, Set<Integer> ids) {
    List<CheckBoxView> data = new ArrayList<>();
    for(CANMessage message: spec.getMessages()) {
      if(ids.contains(message.getId())) {
        for (CANDataField field : message.getData()) {
          data.add(new CheckBoxView(message, field));
        }
      }
    }
    return data;
  }
}
