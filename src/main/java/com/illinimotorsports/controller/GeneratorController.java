package com.illinimotorsports.controller;

import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.model.MessageCheckBoxListModel;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.generate.CANHeaderGenerator;
import com.illinimotorsports.model.generate.CANParserGenerator;
import com.illinimotorsports.view.GeneratedCodeView;
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
    view.getAppPanel().getGenParserButton().addActionListener(e -> openMessageSelector());
    view.getAppPanel().getGenHeaderButton().addActionListener(e -> generateHeaderListener());
  }

  /**
   * Action listener to handle file choosing
   */
  public void openFileChooser() {
    JFileChooser fc = view.getAppPanel().getFileChooser();
    int fileRet = fc.showOpenDialog(view.getAppPanel());
    if(fileRet == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      model.generateModel(file);
    }
  }

  /**
   * Action listener for selecting messages for the parse function
   */
  public void openMessageSelector() {
    selectModel = new MessageCheckBoxListModel(model.getCanSpec());
    MessageCheckBoxList listView = new MessageCheckBoxList(selectModel);
    selectView = new MessageSelect(listView);
    selectView.getSelectAllButton().addActionListener(e -> selectView.getList().setAll(true));
    selectView.getDeselectAllButton().addActionListener(e -> selectView.getList().setAll(false));
    selectView.getSubmitButton().addActionListener(e -> selectorDoneListener());
    selectView.init();
  }

  /**
   * Action Listener for generating parse function
   */
  public void selectorDoneListener() {
    selectView.setVisible(false);
    List<CANMessage> messages = selectModel.getSelectedMessages();
    CANParserGenerator parserGenerator = new CANParserGenerator(messages);
    GeneratedCodeView genCode = new GeneratedCodeView(parserGenerator.fillTemplate());
    genCode.init();
  }

  /**
   * Action Listener for header generator
   */
  public void generateHeaderListener() {
    CANHeaderGenerator headerGenerator = new CANHeaderGenerator(model.getCanSpec());
    GeneratedCodeView genCode = new GeneratedCodeView(headerGenerator.fillTemplate());
    genCode.init();
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
