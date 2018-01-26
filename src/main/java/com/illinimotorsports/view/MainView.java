package com.illinimotorsports.view;

import javax.swing.*;

/**
 * Top level view class
 */
public class MainView extends JFrame {

  private JButton openFileButton;
  private JButton genHeaderButton;
  private JButton genParserButton;
  private JButton genDBCButton;
  private JButton genMessageDocumentationButton;
  private JButton genFieldDocumentationButton;
  private JButton genTelemetryJson;
  private JButton parseLogFilesButton;
  private JFileChooser fileChooser;
  private JLabel canSpecStatus;

  /**
   * Initializes app panel and main view frame
   */
  public MainView() {
    super("FSAE CAN Generator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    fileChooser = new JFileChooser();

    canSpecStatus = new JLabel("No Spec Loaded");
    openFileButton = new JButton("Open CAN Spec");
    genHeaderButton = new JButton("Generate Header");
    genParserButton = new JButton("Generate Parser");
    genDBCButton = new JButton("Generate DBC");
    genTelemetryJson = new JButton("Generate Telemetry JSON");
    parseLogFilesButton = new JButton("Parse Log Files");
    genMessageDocumentationButton = new JButton("Message Documentation");
    genFieldDocumentationButton = new JButton("Field Documentation");
    setEnableGenButtons(false);
  }

  /**
   * Sets app panel to be viewable
   */
  public void init() {
    JPanel openButtonPanel = new JPanel();
    JPanel genButtonPanel1 = new JPanel();
    JPanel genButtonPanel2 = new JPanel();
    JPanel genButtonPanel3 = new JPanel();
    JPanel topLevelPanel = new JPanel();
    topLevelPanel.setLayout(new BoxLayout(topLevelPanel, BoxLayout.PAGE_AXIS));
    openButtonPanel.add(openFileButton);
    openButtonPanel.add(canSpecStatus);
    genButtonPanel1.add(genHeaderButton);
    genButtonPanel1.add(genParserButton);
    genButtonPanel1.add(genDBCButton);
    genButtonPanel2.add(genMessageDocumentationButton);
    genButtonPanel2.add(genFieldDocumentationButton);
    genButtonPanel3.add(genTelemetryJson);
    genButtonPanel3.add(parseLogFilesButton);
    topLevelPanel.add(openButtonPanel);
    topLevelPanel.add(genButtonPanel1);
    topLevelPanel.add(genButtonPanel2);
    topLevelPanel.add(genButtonPanel3);

    add(topLevelPanel);
    pack();
    setVisible(true);
  }

  public void setEnableGenButtons(boolean enable) {
    genHeaderButton.setEnabled(enable);
    genParserButton.setEnabled(enable);
    genDBCButton.setEnabled(enable);
    genMessageDocumentationButton.setEnabled(enable);
    genFieldDocumentationButton.setEnabled(enable);
    genTelemetryJson.setEnabled(enable);
    parseLogFilesButton.setEnabled(enable);
  }

  public JButton getOpenFileButton() {
    return openFileButton;
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

  public JButton getGenMessageDocumentationButton() {
    return genMessageDocumentationButton;
  }

  public JButton getGenFieldDocumentationButton() {
    return genFieldDocumentationButton;
  }

  public JFileChooser getFileChooser() {
    return fileChooser;
  }

  public JLabel getCanSpecStatus() {
    return canSpecStatus;
  }

  public JButton getParseLogFilesButton() {
    return parseLogFilesButton;
  }

  public JButton getGenTelemetryJson() {
    return genTelemetryJson;
  }
}
