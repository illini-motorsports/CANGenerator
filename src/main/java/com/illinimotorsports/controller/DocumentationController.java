package com.illinimotorsports.controller;

import com.illinimotorsports.model.DocumentationTableModel;
import com.illinimotorsports.view.DocumentationTableView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Controller class for generated code windows
 * Handles saving and copying to clipboard of text
 */
public class DocumentationController {

  private DocumentationTableView view;
  private DocumentationTableModel model;

  /**
   * Constructor for GeneratedCodeController
   * @param theModel
   */
  public DocumentationController(DocumentationTableModel theModel) {
    model = theModel;
    view = new DocumentationTableView();
  }

  /**
   * Initializes model and view
   */
  public void init(boolean isColored) {
    model.addRows();
    view.getTable().setModel(model);

    if(isColored) { // Use colored cell renderer if table should be colored
      for(int i = 0; i < view.getTable().getColumnModel().getColumnCount(); i++) {
        view.getTable().getColumnModel().getColumn(i).setCellRenderer(new ColoredCellRenderer());
      }
    }
    view.getTable().setShowGrid(true);

    // Add action listeners for buttons
    view.getDoneButton().addActionListener(e -> closeWindow());
    view.getSaveButton().addActionListener(e -> saveToFile());

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
        // Attempt to write the CSV to specified file
        FileWriter fw = new FileWriter(fc.getSelectedFile());
        fw.write(model.getCSV());
        message.showMessageDialog(view, "Success", ":)", JOptionPane.INFORMATION_MESSAGE);
        fw.close();
      } catch (IOException e) {
        // Catch exception and show error message upon failure
        message.showMessageDialog(view, "File Write Error!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Custom cell renderer used to color cells for documentation
   */
  protected class ColoredCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      // Retain normal defaultTableCellRenderer behavior
      // except set background color to what's indicated in the model
      cellComponent.setBackground(model.getCellColor(row, column));
      return cellComponent;
    }
  }
}
