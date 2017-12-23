package com.illinimotorsports.controller;

import com.illinimotorsports.model.DocumentationTableModel;
import com.illinimotorsports.model.MainModel;
import com.illinimotorsports.model.generate.*;
import com.illinimotorsports.model.parse.CANParseException;
import com.illinimotorsports.view.MainView;

import javax.swing.*;
import java.io.File;

/**
 * Main controller for the application
 */
public class MainController {

  private MainView view;
  private MainModel model;

  /**
   * Constructor for controller, takes in model and view
   */
  public MainController() {
    this.view = new MainView();
    this.model = new MainModel();
  }

  /**
   * Sets up controller, adds action listeners, etc
   */
  public void init() {
    // Add action listeners for all the buttons
    view.getOpenFileButton().addActionListener(e -> openFileListener());
    view.getGenParserButton().addActionListener(e -> openMessageSelectorListener(new CParserGenerator()));
    view.getGenHeaderButton().addActionListener(e -> generateHeaderListener());
    view.getGenDBCButton().addActionListener(e -> generateDBCListener());
    view.getGenMessageDocumentationButton().addActionListener(e -> generateMessageDocumentationListener());
    view.getGenFieldDocumentationButton().addActionListener(e -> generateFieldDocumentationListener());

    view.init();
  }

  /**
   * Action listener to handle file choosing
   */
  public void openFileListener() {
    JFileChooser fc = view.getFileChooser();

    // Show file chooser and continue if user selects a file
    if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      try {
        // Attempt to parse spec
        model.generateModel(file);

        // Any issues with parsing will throw an exception,
        // so if this code is executed, the spec was parsed successfully
        view.getCanSpecStatus().setText("Spec Successfully Parsed!");
        view.getOpenFileButton().setText("Open New CAN Spec");

        // Allow users to press other buttons
        view.setEnableGenButtons(true);
      } catch (CANParseException e) {
        // There was a parse error,
        // so display an error message and disable buttons
        JOptionPane parseError = new JOptionPane();
        parseError.showMessageDialog(null, e.getMessage(), "CAN Parse Error :(", JOptionPane.ERROR_MESSAGE);
        view.getCanSpecStatus().setText("CAN Parse Error :(");
        view.setEnableGenButtons(false);
      }
    }
  }

  /**
   * Action listener for selecting messages for the parse function
   * @param generator The code generator that should be associated with the selector
   */
  public void openMessageSelectorListener(SelectedMessagesGenerator generator) {
    MessageSelectController messageSelectController = new MessageSelectController(model.getCanSpec());
    messageSelectController.init(generator);
  }

  /**
   * Action Listener for header generator
   */
  public void generateHeaderListener() {
    TemplatedGenerator gen = new CHeaderGenerator(model.getCanSpec());
    GeneratedCodeController genCode = new GeneratedCodeController(gen);
    genCode.init();
  }

  /**
   * Listener for DBC generator window
   */
  public void generateDBCListener() {
    TemplatedGenerator gen = new DBCGenerator(model.getCanSpec());
    GeneratedCodeController genCode = new GeneratedCodeController(gen);
    genCode.init();
  }

  /**
   * Listener for documentation generator window
   */
  public void generateMessageDocumentationListener() {
    DocumentationGenerator generator = new DocumentationGenerator(model.getCanSpec());
    DocumentationTableModel model = new DocumentationTableModel(DocumentationGenerator.messageTableColumns, generator.generateMessageTable());
    DocumentationController controller = new DocumentationController(model);
    controller.init(true);
  }

  /**
   * Listener for documentation generator window
   */
  public void generateFieldDocumentationListener() {
    DocumentationGenerator generator = new DocumentationGenerator(model.getCanSpec());
    DocumentationTableModel model = new DocumentationTableModel(DocumentationGenerator.fieldTableColumns, generator.generateFieldTable());
    DocumentationController controller = new DocumentationController(model);
    controller.init(false);
  }

  /**
   * Main function, sets up MVC and initializes view in separate thread
   * @param args
   */
  public static void main(String[] args) {
    MainController controller = new MainController();
    javax.swing.SwingUtilities.invokeLater(() -> controller.init());
  }
}
