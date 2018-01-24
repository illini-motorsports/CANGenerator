package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

/**
 * Custom panel for checkbox list
 */
public abstract class CheckBoxView extends JPanel {

  private JCheckBox checkBox;
  private JTextArea text;

  public CheckBoxView() {
    checkBox = new JCheckBox();
    text = new JTextArea();
    setLayout(new BorderLayout());
    add(checkBox, BorderLayout.NORTH);
    add(text, BorderLayout.SOUTH);
  }

  public void setCheckBoxText(String checkBoxText) {
    checkBox.setText(checkBoxText);
  }

  public void setTextArea(String textArea) {
    text.setText(textArea);
  }

  public JCheckBox getCheckBox() {
    return checkBox;
  }

  public abstract Object getData();
}
