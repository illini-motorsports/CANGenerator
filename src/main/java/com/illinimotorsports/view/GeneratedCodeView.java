package com.illinimotorsports.view;

import javax.swing.*;

public class GeneratedCodeView extends JFrame {
  private JTextArea code;

  public GeneratedCodeView(String generatedCode) {
    code = new JTextArea(generatedCode);
  }

  public void init() {
    this.add(new JScrollPane(code));
    this.pack();
    this.setVisible(true);
  }
}
