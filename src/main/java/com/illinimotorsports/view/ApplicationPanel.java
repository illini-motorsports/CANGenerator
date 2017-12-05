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
    JPanel openButtonPanel = new JPanel();
    JPanel genButtonPanel = new JPanel();
    openButtonPanel.add(openFileButton);
    openButtonPanel.add(canSpecStatus);
    genButtonPanel.add(genHeaderButton);
    genButtonPanel.add(genParserButton);
    genButtonPanel.add(genDBCButton);
    add(openButtonPanel, BorderLayout.PAGE_START);
    add(genButtonPanel, BorderLayout.CENTER);
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
  }
}
