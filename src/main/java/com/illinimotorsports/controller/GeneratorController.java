package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.view.MainView;

import javax.swing.*;
import java.io.File;

/**
 * Main controller for the application
 */
public class GeneratorController {

  private MainView view;
  private GeneratorModel model;

  /**
   * Constructor for controller, takes in model and view
   * @param v
   * @param m
   */
  public GeneratorController(MainView v, GeneratorModel m) {
    this.view = v;
    this.model = m;
  }

  /**
   * Initializes view to main application panel
   */
  public void initView() {
    view.addAppPanel();
  }

  /**
   * Sets up controller, adds action listeners, etc
   */
  public void initController() {
    view.getAppPanel().getOpenFileButton().addActionListener(e -> openFileChooser());
  }

  /**
   * Action listener to handle file choosing
   */
  public void openFileChooser() {
    JFileChooser fc = view.getAppPanel().getFileChooser();
    JTextArea la = view.getAppPanel().getLogArea();
    int fileRet = fc.showOpenDialog(view.getAppPanel());
    if(fileRet == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      model.generateModel(file);
      la.append(model.getCanSpec().getMessagesWithFields().toString());
    } else {
      la.append("User Canceled ");
    }
    la.setCaretPosition(la.getDocument().getLength());
  }

  /**
   * Main function, sets up MVC and initializes view in seperate thread
   * @param args
   */
  public static void main(String[] args) {
    MainView v = new MainView();
    GeneratorModel m = new GeneratorModel();
    GeneratorController controller = new GeneratorController(v, m);
    controller.initController();
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        controller.initView();
      }
    });
  }
}
