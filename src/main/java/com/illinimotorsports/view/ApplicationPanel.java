package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application JPanel
 */
public class ApplicationPanel extends JPanel {

  JButton openFileButton;
  JTextArea logArea;
  JFileChooser fileChooser;

  /**
   * Initializes all components
   */
  public ApplicationPanel() {
    super(new BorderLayout());
    logArea = new JTextArea(5,20);
    logArea.setMargin(new Insets(5,5,5,5));
    logArea.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(logArea);

    fileChooser = new JFileChooser();

    openFileButton = new JButton("Open CAN Spec");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(openFileButton);
    add(buttonPanel, BorderLayout.PAGE_START);
    add(logScrollPane, BorderLayout.CENTER);
  }

  /**
   * Accessor for button to set actionListener
   * @return
   */
  public JButton getOpenFileButton() {
    return openFileButton;
  }

  public JTextArea getLogArea() {
    return logArea;
  }

  public JFileChooser getFileChooser() {
    return fileChooser;
  }
}
