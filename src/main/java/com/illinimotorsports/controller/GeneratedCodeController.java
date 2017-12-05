package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratedCodeModel;
import com.illinimotorsports.view.GeneratedCodeView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;

public class GeneratedCodeController {
  private GeneratedCodeModel model;
  private GeneratedCodeView view;

  public GeneratedCodeController(GeneratedCodeModel theModel, GeneratedCodeView theView) {
    model = theModel;
    view = theView;
  }

  public void init() {
    view.getCode().setText(model.getCode());
    view.getDoneButton().addActionListener(e -> closeWindow());
    view.getSaveButton().addActionListener(e -> saveToFile());
    view.getCopyButton().addActionListener(e -> copyToClipboard());
    view.init();
  }

  public void closeWindow() {
    view.setVisible(false);
  }

  public void saveToFile() {
    JFileChooser fc = view.getFc();
    if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      JOptionPane message = new JOptionPane();
      try {
        FileWriter fw = new FileWriter(fc.getSelectedFile());
        fw.write(model.getCode());
        message.showMessageDialog(view, "Success", ":)", JOptionPane.INFORMATION_MESSAGE);
        fw.close();
      } catch (IOException e) {
        message.showMessageDialog(view, "File Write Error!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void copyToClipboard() {
    Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(
            new StringSelection(model.getCode()),null);
  }
}
