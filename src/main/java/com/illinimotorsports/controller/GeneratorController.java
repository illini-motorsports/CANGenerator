package com.illinimotorsports.controller;

import com.illinimotorsports.model.DocumentationTableModel;
import com.illinimotorsports.model.GeneratedCodeModel;
import com.illinimotorsports.model.GeneratorModel;
import com.illinimotorsports.model.MessageCheckBoxListModel;
import com.illinimotorsports.model.canspec.CANMessage;
import com.illinimotorsports.model.generate.CANHeaderGenerator;
import com.illinimotorsports.model.generate.CANParserGenerator;
import com.illinimotorsports.model.generate.DBCGenerator;
import com.illinimotorsports.model.generate.DocumentationGenerator;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.view.*;

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
    view.getAppPanel().getOpenFileButton().addActionListener(e -> chooseCanSpec());
    view.getAppPanel().getGenParserButton().addActionListener(e -> openMessageSelector());
    view.getAppPanel().getGenHeaderButton().addActionListener(e -> generateHeaderListener());
    view.getAppPanel().getGenDBCButton().addActionListener(e -> generateDBCListener());
    view.getAppPanel().getGenDocumentationButton().addActionListener(e -> generateDocumentationListener());
  }

  /**
   * Action listener to handle file choosing
   */
  public void chooseCanSpec() {
    JFileChooser fc = view.getAppPanel().getFileChooser();
    int fileRet = fc.showOpenDialog(view.getAppPanel());
    if(fileRet == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        model.generateModel(file);
        view.getAppPanel().getCanSpecStatus().setText("Spec Successfully Parsed!");
        view.getAppPanel().setEnableGenButtons(true);
      } catch (CANParseException e) {
        JOptionPane parseError = new JOptionPane();
        parseError.showMessageDialog(view.getAppPanel(), e.getMessage(), "CAN Parse Error :(", JOptionPane.ERROR_MESSAGE);
        view.getAppPanel().getCanSpecStatus().setText("CAN Parse Error :(");
      }
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
    GeneratedCodeController genCode = new GeneratedCodeController(
        new GeneratedCodeModel(parserGenerator.fillTemplate()),
        new GeneratedCodeView());
    genCode.init();
  }

  /**
   * Action Listener for header generator
   */
  public void generateHeaderListener() {
    CANHeaderGenerator headerGenerator = new CANHeaderGenerator(model.getCanSpec());
    GeneratedCodeController genCode = new GeneratedCodeController(
        new GeneratedCodeModel(headerGenerator.fillTemplate()),
        new GeneratedCodeView());
    genCode.init();
  }

  public void generateDBCListener() {
    DBCGenerator gen = new DBCGenerator(model.getCanSpec());
    GeneratedCodeController genCode = new GeneratedCodeController(
        new GeneratedCodeModel(gen.fillTemplate()),
        new GeneratedCodeView()
    );
    genCode.init();
  }

  public void generateDocumentationListener() {
    DocumentationGenerator generator = new DocumentationGenerator(model.getCanSpec());
    DocumentationTableModel model = new DocumentationTableModel(
        DocumentationGenerator.messageTableColumns,
        generator.generateMessageTable());
    DocumentationTableView view = new DocumentationTableView();
    view.getTable().setModel(model);
    view.init();
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
