package com.illinimotorsports.view;

import javax.swing.*;

/**
 * Generic view for showing generated code.
 * TODO: Syntax highlighting, copy to clipboard button
 */
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
