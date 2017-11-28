package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.model.MessageCheckBoxListModel;
import com.illinimotorsports.model.generate.CANHeaderGenerator;
import com.illinimotorsports.model.generate.CANParserGenerator;
import com.illinimotorsports.view.MainView;
import com.illinimotorsports.view.MessageCheckBoxList;
import com.illinimotorsports.view.MessageSelect;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * Main controller for the application
 */
public class GeneratorController {

  private MainView view;
  private GeneratorModel model;
  private MessageSelect selectView;
  private MessageCheckBoxListModel selectModel;

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
      la.append(messageList.toString() + "\n");
      CANHeaderGenerator generator = new CANHeaderGenerator(model.getCanSpec());
      System.out.println(generator.fillTemplate());
      CANParserGenerator parserGenerator = new CANParserGenerator(model.getCanSpec().getMessages());
      System.out.println(parserGenerator.fillTemplate());
      openMessageSelector();
    } else {
      la.append("User Canceled ");
    }
    la.setCaretPosition(la.getDocument().getLength());
  }

  public void openMessageSelector() {
    selectModel = new MessageCheckBoxListModel(model.getCanSpec());
    MessageCheckBoxList listView = new MessageCheckBoxList(selectModel);
    selectView = new MessageSelect(listView);
    selectView.getSelectAllButton().addActionListener(e -> selectView.getList().setAll(true));
    selectView.getDeselectAllButton().addActionListener(e -> selectView.getList().setAll(false));
    selectView.getSubmitButton().addActionListener(e -> selectorDoneListener());
    selectView.init();
  }

  public void selectorDoneListener() {
    view.getAppPanel().getLogArea().append(selectModel.getSelectedMessages().toString());
    selectView.setVisible(false);
  }

  /**
   * Main function, sets up MVC and initializes view in separate thread
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
