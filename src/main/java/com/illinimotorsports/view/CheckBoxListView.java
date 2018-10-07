package com.illinimotorsports.view;

import com.illinimotorsports.model.SelectModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom checkbox list to allow setting and unsetting all of the checkboxes
 * templated with custom messagecheckbox panel
 */
public class CheckBoxListView extends JList<CheckBoxView> {

  public CheckBoxListView(SelectModel model) {
    setModel(model);
    setCellRenderer(new CheckBoxCellRenderer());
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        int index = locationToIndex(e.getPoint());
        if(index >= 0) {
          JCheckBox box = getModel().getElementAt(index).getCheckBox();
          box.setSelected(!box.isSelected());
        }
        repaint();
      }
    });
  }

  /**
   * Set or unset all boxes
   * @param selected
   */
  public void setAll(boolean selected) {
    ((SelectModel) getModel()).setAll(selected);
    repaint();
  }

  /**
   * protected inner class to render cells
   */
  protected class CheckBoxCellRenderer implements ListCellRenderer<CheckBoxView> {
    @Override
    public Component getListCellRendererComponent(JList<? extends CheckBoxView> list, CheckBoxView value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
      Color background = isSelected ? Color.GRAY : Color.WHITE;
      value.setBackground(background);
      return value;
    }
  }
}
