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
  private MessageSelectView selectView;
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
    view.init();
  }

  /**
   * Sets up controller, adds action listeners, etc
   */
  public void initController() {
    view.getOpenFileButton().addActionListener(e -> chooseCanSpec());
    view.getGenParserButton().addActionListener(e -> openMessageSelector());
    view.getGenHeaderButton().addActionListener(e -> generateHeaderListener());
    view.getGenDBCButton().addActionListener(e -> generateDBCListener());
    view.getGenMessageDocumentationButton().addActionListener(e -> generateMessageDocumentationListener());
    view.getGenFieldDocumentationButton().addActionListener(e -> generateFieldDocumentationListener());
  }

  /**
   * Action listener to handle file choosing
   */
  public void chooseCanSpec() {
    JFileChooser fc = view.getFileChooser();
    int fileRet = fc.showOpenDialog(null); // Might need topLevelPanel as parent
    if(fileRet == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        model.generateModel(file);
        view.getCanSpecStatus().setText("Spec Successfully Parsed!");
        view.getOpenFileButton().setText("Open New CAN Spec");
        view.setEnableGenButtons(true);
      } catch (CANParseException e) {
        JOptionPane parseError = new JOptionPane();
        parseError.showMessageDialog(null, e.getMessage(), "CAN Parse Error :(", JOptionPane.ERROR_MESSAGE);
        view.getCanSpecStatus().setText("CAN Parse Error :(");
      }
    }
  }

  /**
   * Action listener for selecting messages for the parse function
   */
  public void openMessageSelector() {
    selectModel = new MessageCheckBoxListModel(model.getCanSpec());
    MessageCheckBoxListView listView = new MessageCheckBoxListView(selectModel);
    selectView = new MessageSelectView(listView);
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

  /**
   * Listener for DBC generator window
   */
  public void generateDBCListener() {
    DBCGenerator gen = new DBCGenerator(model.getCanSpec());
    GeneratedCodeController genCode = new GeneratedCodeController(
        new GeneratedCodeModel(gen.fillTemplate()),
        new GeneratedCodeView()
    );
    genCode.init();
  }

  /**
   * Listener for documentation generator window
   */
  public void generateMessageDocumentationListener() {
    DocumentationGenerator generator = new DocumentationGenerator(model.getCanSpec());
    DocumentationController controller = new DocumentationController(new DocumentationTableModel(
        DocumentationGenerator.messageTableColumns,
        generator.generateMessageTable()),
        new DocumentationTableView()
    );
    controller.init(true);
  }

  /**
   * Listener for documentation generator window
   */
  public void generateFieldDocumentationListener() {
    DocumentationGenerator generator = new DocumentationGenerator(model.getCanSpec());
    DocumentationController controller = new DocumentationController(new DocumentationTableModel(
        DocumentationGenerator.fieldTableColumns,
        generator.generateFieldTable()),
        new DocumentationTableView()
    );
    controller.init(false);
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
