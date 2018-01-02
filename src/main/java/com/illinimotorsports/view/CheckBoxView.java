package com.illinimotorsports.view;

import com.illinimotorsports.model.canspec.CANBitmapField;
import com.illinimotorsports.model.canspec.CANDataField;
import com.illinimotorsports.model.canspec.CANMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Custom panel for checkbox list
 */
public class CheckBoxView extends JPanel {

  private JCheckBox checkBox;
  private JTextArea text;
  private CANMessage message;
  private CANDataField field;

  /**
   * show all the fields underneath the box
   * @param canMessage
   */
  public CheckBoxView(CANMessage canMessage) {
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

  public CheckBoxView(CANMessage canMessage, CANDataField canField) {
    message = canMessage;
    field = canField;
    checkBox = new JCheckBox(" 0x" + Integer.toHexString(message.getId())
        + " " + canMessage.getNode() + " " + canField.getName());
    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.NORTH);
    if(canField instanceof CANBitmapField) {
      // TODO: make this less shitty
      CANBitmapField bitmapField = (CANBitmapField) canField;
      String bitText = "";
      for(int i = 0; i < bitmapField.getBits().size() - 1; i++) {
        if(i % 4 == 0 && i != 0) {
          bitText += "\n";
        }
        bitText += "  " + bitmapField.getBits().get(i).getName() + ",";
      }
      bitText += "  " + bitmapField.getBits().get(bitmapField.getBits().size()-1).getName();
      text = new JTextArea(bitText);
      add(text, BorderLayout.SOUTH);
    }
  }

  public JCheckBox getCheckBox() {
    return checkBox;
  }

  public CANMessage getMessage() {
    return message;
  }
}
