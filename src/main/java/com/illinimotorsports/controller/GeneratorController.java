package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.view.MainView;

import javax.swing.*;
import java.io.File;

public class GeneratorController {

  private MainView view;
  private GeneratorModel model;

  public GeneratorController(MainView v) {
    this.view = v;
    this.model = new GeneratorModel();
  }

  public void initView() {
    view.createAndShowGUI();
  }

  public void initController() {
    view.getOpenButton().addActionListener(e -> openFileChooser());
  }

  public void openFileChooser() {
    int fileRet = view.getFileChooser().showOpenDialog(view);
    if(fileRet == JFileChooser.APPROVE_OPTION) {
      File file = view.getFileChooser().getSelectedFile();
      model.getParser().setCanSpec(file);
      view.getLogArea().append(model.getParser().getFields().toString());
    } else {
      view.getLogArea().append("User Canceled ");
    }
    view.getLogArea().setCaretPosition(view.getLogArea().getDocument().getLength());
  }

  public static void main(String[] args) {
    GeneratorController controller = new GeneratorController(new MainView());
    controller.initController();
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        controller.initView();
      }
    });
  }
}
