package com.illinimotorsports.view;

import com.illinimotorsports.model.MessageCheckBoxListModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MessageCheckBoxList extends JList<MessageCheckBox> {

  public MessageCheckBoxList(MessageCheckBoxListModel model) {
    setModel(model);
    setCellRenderer(new CheckBoxCellRenderer());
    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        int index = locationToIndex(e.getPoint());
        JCheckBox box = getModel().getElementAt(index).getCheckBox();
        box.setSelected(!box.isSelected());
        repaint();
      }
    });
  }

  public void setAll(boolean selected) {
    ((MessageCheckBoxListModel) getModel()).setAll(selected);
    repaint();
  }

  protected class CheckBoxCellRenderer implements ListCellRenderer<MessageCheckBox> {
    @Override
    public Component getListCellRendererComponent(JList<? extends MessageCheckBox> list, MessageCheckBox value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
      Color background = isSelected ? Color.GRAY : Color.WHITE;
      value.setBackground(background);
      return value;
    }
  }
}
