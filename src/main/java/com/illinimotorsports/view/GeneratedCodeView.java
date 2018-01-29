package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

/**
 * Generic view for showing generated code.
 */
public class GeneratedCodeView extends JFrame {
  private JTextArea code;
  private JButton saveButton;
  private JButton copyButton;
  private JButton doneButton;
  private JFileChooser fc;

  /**
   * Constructor for Generated Code View
   */
  public GeneratedCodeView() {
    code = new JTextArea();
    code.setEditable(false);
    saveButton = new JButton("Save to File");
    copyButton = new JButton("Copy to Clipboard");
    doneButton = new JButton("Done");
    fc = new JFileChooser();
    this.setLayout(new BorderLayout());
  }

  /**
   * Initializes view
   */
  public void init() {
    this.add(new JScrollPane(code));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);
    buttonPanel.add(copyButton);
    buttonPanel.add(doneButton);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.pack();
    this.setVisible(true);
  }

  public JTextArea getCode() {
    return code;
  }

  public JButton getSaveButton() {
    return saveButton;
  }

  public JButton getCopyButton() {
    return copyButton;
  }

  public JButton getDoneButton() {
    return doneButton;
  }

  public JFileChooser getFc() {
    return fc;
  }
}
