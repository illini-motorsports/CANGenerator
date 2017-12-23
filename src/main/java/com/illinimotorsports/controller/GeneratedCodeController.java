package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratedCodeModel;
import com.illinimotorsports.model.generate.TemplatedGenerator;
import com.illinimotorsports.view.GeneratedCodeView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller class for generated code windows
 * Handles saving and copying to clipboard of text
 */
public class GeneratedCodeController {

  private GeneratedCodeModel model;
  private GeneratedCodeView view;

  /**
   * Constructor for GeneratedCodeController
   */
  public GeneratedCodeController(TemplatedGenerator generator) {
    model = new GeneratedCodeModel(generator);
    view = new GeneratedCodeView();
  }

  /**
   * Initializes model and view
   */
  public void init() {
    // populate text area with code
    view.getCode().setText(model.getCode());

    // Add action listeners for buttons
    view.getDoneButton().addActionListener(e -> closeWindow());
    view.getSaveButton().addActionListener(e -> saveToFile());
    view.getCopyButton().addActionListener(e -> copyToClipboard());

    view.init();
  }

  /**
   * Close window
   */
  public void closeWindow() {
    view.setVisible(false);
  }

  /**
   * Generates file chooser window, writes generated code to selected file,
   * handles any resulting errors
   */
  public void saveToFile() {
    JFileChooser fc = view.getFc();

    // Show file chooser and continue if user selects a file
    if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      JOptionPane message = new JOptionPane();
      try {
        // Attempt to write the code to the specified file
        FileWriter fw = new FileWriter(fc.getSelectedFile());
        fw.write(model.getCode());
        message.showMessageDialog(view, "Success", ":)", JOptionPane.INFORMATION_MESSAGE);
        fw.close();
      } catch (IOException e) {
        // Catch exception and show error message upon failure
        message.showMessageDialog(view, "File Write Error!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Copies generated code to clipboard
   */
  public void copyToClipboard() {
    // Insert code into clipboard using toolkit library
    Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(new StringSelection(model.getCode()),null);
  }
}
