package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MessageCheckBox extends JPanel {

  private JCheckBox checkBox;
  private JTextArea text;

  public MessageCheckBox(String messageName, List<String> fields) {
    checkBox = new JCheckBox(messageName);

    String fieldText = "Fields:\n";
    for(String field: fields) {
      fieldText += "\t" + field + "\n";
    }

    text = new JTextArea(fieldText);

    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.NORTH);
    add(text, BorderLayout.SOUTH);
  }

  public JCheckBox getCheckBox() {
    return checkBox;
  }
}
