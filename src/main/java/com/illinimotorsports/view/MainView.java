package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

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
    genMessageDocumentationButton = new JButton("Message Documentation");
    genFieldDocumentationButton = new JButton("Field Documentation");
  }

  /**
   * Sets app panel to be viewable
   */
  public void init() {
    JPanel openButtonPanel = new JPanel();
    JPanel genButtonPanel1 = new JPanel();
    JPanel genButtonPanel2 = new JPanel();
    JPanel topLevelPanel = new JPanel(new BorderLayout());
    openButtonPanel.add(openFileButton);
    openButtonPanel.add(canSpecStatus);
    genButtonPanel1.add(genHeaderButton);
    genButtonPanel1.add(genParserButton);
    genButtonPanel1.add(genDBCButton);
    genButtonPanel2.add(genMessageDocumentationButton);
    genButtonPanel2.add(genFieldDocumentationButton);
    topLevelPanel.add(openButtonPanel, BorderLayout.PAGE_START);
    topLevelPanel.add(genButtonPanel1, BorderLayout.CENTER);
    topLevelPanel.add(genButtonPanel2, BorderLayout.SOUTH);

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
}
