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
  JFileChooser fileChooser;

  /**
   * Initializes all components
   */
  public ApplicationPanel() {
    super(new BorderLayout());

    fileChooser = new JFileChooser();

    openFileButton = new JButton("Open CAN Spec");
    genHeaderButton = new JButton("Generate Header");
    genParserButton = new JButton("Generate Parser");
    JPanel openButtonPanel = new JPanel();
    JPanel genButtonPanel = new JPanel();
    openButtonPanel.add(openFileButton);
    genButtonPanel.add(genHeaderButton);
    genButtonPanel.add(genParserButton);
    add(openButtonPanel, BorderLayout.PAGE_START);
    add(genButtonPanel, BorderLayout.CENTER);
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
}
