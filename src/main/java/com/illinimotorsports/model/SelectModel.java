package com.illinimotorsports.model;

import com.illinimotorsports.view.CheckBoxView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model for checkbox list
 * Has special functions for getting selected messages and setting all boxes
 */
public class SelectModel<T extends CheckBoxView> extends AbstractListModel<T> {

  private List<T> data;

  /**
   * Sets up internal data structure
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
    data.forEach(x -> x.getCheckBox().setSelected(selected));
  }

  /**
   * Returns a list of CANMessages that were selected
   * @return
   */
  public List<CheckBoxView> getSelectedMessages() {
    return data.stream().filter(x -> x.getCheckBox().isSelected()).collect(Collectors.toList());
  }

  @Override
  public int getSize() {
    return data.size();
  }

  @Override
  public T getElementAt(int index) {
    return data.get(index);
  }

}
