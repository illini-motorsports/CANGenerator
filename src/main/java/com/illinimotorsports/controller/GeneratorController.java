package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.model.MessageCheckBoxListModel;
import com.illinimotorsports.view.MessageCheckBoxList;
import com.illinimotorsports.view.MainView;
import com.illinimotorsports.view.MessageCheckBox;

import javax.swing.*;
import java.io.File;
import java.util.List;

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
      List<List<String>> messageList = model.getCanSpec().getMessagesWithFields();
      la.append(messageList.toString());
      openMessageSelector(messageList);
    } else {
      la.append("User Canceled ");
    }
    la.setCaretPosition(la.getDocument().getLength());
  }

  public void openMessageSelector(List<List<String>> messageList) {
      MessageCheckBoxList listView = new MessageCheckBoxList(new MessageCheckBoxListModel(messageList));
      JFrame listFrame = new JFrame("Checkbox");
      listFrame.add(new JScrollPane(listView));
      listFrame.pack();
      listFrame.setVisible(true);
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
    javax.swing.SwingUtilities.invokeLater(() -> controller.initView());
  }
}
