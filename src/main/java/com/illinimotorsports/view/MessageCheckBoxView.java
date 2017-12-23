package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Custom panel for checkbox list
 */
public class MessageCheckBoxView extends JPanel {

  private JCheckBox checkBox;
  private JTextArea text;
  private CANMessage message;

  /**
   * show all the fields underneath the box
   * @param canMessage
   */
  public MessageCheckBoxView(CANMessage canMessage) {
    message = canMessage;
    checkBox = new JCheckBox(message.getNode() +
        " 0x" + Integer.toHexString(message.getId()));

    String fieldText = "Fields:\n";
    List<String> fieldNames = message.getFieldNames();
    for(int i = 0; i < fieldNames.size() - 1; i++) {
      if(i % 4 == 0 && i != 0) {
        fieldText += "\n";
      }
      fieldText += "  " + fieldNames.get(i) + ",";
    }
    fieldText += "  " + fieldNames.get(fieldNames.size()-1);

    text = new JTextArea(fieldText);

    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.NORTH);
    add(text, BorderLayout.SOUTH);
  }

  public JCheckBox getCheckBox() {
    return checkBox;
  }

  public CANMessage getMessage() {
    return message;
  }
}
