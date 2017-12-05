package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application JPanel
 */
public class ApplicationPanel extends JPanel {

  JButton openFileButton;
  JButton genHeaderButton;
  JButton genParserButton;
  JButton genDBCButton;
  JButton genDocumentationButton;
  JFileChooser fileChooser;
  JLabel canSpecStatus;

  /**
   * Initializes all components
   */
  public ApplicationPanel() {
    super(new BorderLayout());

    fileChooser = new JFileChooser();

    canSpecStatus = new JLabel("No Spec Loaded");
    openFileButton = new JButton("Open CAN Spec");
    genHeaderButton = new JButton("Generate Header");
    genParserButton = new JButton("Generate Parser");
    genDBCButton = new JButton("Generate DBC");
    genDocumentationButton = new JButton("Generate Documenation");
    JPanel openButtonPanel = new JPanel();
    JPanel genButtonPanel1 = new JPanel();
    JPanel genButtonPanel2 = new JPanel();
    openButtonPanel.add(openFileButton);
    openButtonPanel.add(canSpecStatus);
    genButtonPanel1.add(genHeaderButton);
    genButtonPanel1.add(genParserButton);
    genButtonPanel2.add(genDBCButton);
    genButtonPanel2.add(genDocumentationButton);
    add(openButtonPanel, BorderLayout.PAGE_START);
    add(genButtonPanel1, BorderLayout.CENTER);
    add(genButtonPanel2, BorderLayout.SOUTH);
    setEnableGenButtons(false);
  }

  /**
   * Accessor for button to set actionListener
   * @return
   */
  public JButton getOpenFileButton() {
    return openFileButton;
  }

  public JFileChooser getFileChooser() {
    return fileChooser;
  }

  public JButton getGenHeaderButton() {
    return genHeaderButton;
  }

  public JButton getGenParserButton() {
    return genParserButton;
  }

  public JButton getGenDBCButton() {
    return genDBCButton;
  }

  public JLabel getCanSpecStatus() {
    return canSpecStatus;
  }

  public void setEnableGenButtons(boolean enable) {
    genHeaderButton.setEnabled(enable);
    genParserButton.setEnabled(enable);
    genDBCButton.setEnabled(enable);
    genDocumentationButton.setEnabled(enable);
  }

  public JButton getGenDocumentationButton() {
    return genDocumentationButton;
  }
}
