package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {

  public JButton openButton;
  JTextArea logArea;
  JFileChooser fileChooser;

  public MainView() {
    super(new BorderLayout());
    // Set up logging area
    logArea = new JTextArea(5,20);
    logArea.setMargin(new Insets(5,5,5,5));
    logArea.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(logArea);

    // Set up File Chooser
    fileChooser = new JFileChooser();

    // Open Button
    openButton = new JButton("Open JSON");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(openButton);
    add(buttonPanel, BorderLayout.PAGE_START);
    add(logScrollPane, BorderLayout.CENTER);
  }

  public void createAndShowGUI() {
    //Create and set up the window.
    JFrame frame = new JFrame("FSAE CAN Toolbox");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public JButton getOpenButton() {
    return openButton;
  }

  public JTextArea getLogArea() {
    return logArea;
  }

  public JFileChooser getFileChooser() {
    return fileChooser;
  }
}
