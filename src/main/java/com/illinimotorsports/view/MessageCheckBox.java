package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANMessage;

import javax.swing.*;
import java.awt.*;

/**
 * Custom panel for checkbox list
 */
public class MessageCheckBox extends JPanel {

  private JCheckBox checkBox;
  private JTextArea text;
  private CANMessage message;

  /**
   * show all the fields underneath the box
   * @param canMessage
   */
  public MessageCheckBox(CANMessage canMessage) {
    message = canMessage;
    checkBox = new JCheckBox(message.getNode() +
        " 0x" + Integer.toHexString(message.getId()));

    String fieldText = "Fields:\n";
    for(String field: message.getFieldNames()) {
      fieldText += "  " + field + "\n";
    }

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
