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
   * @param theView
   */
  public DocumentationController(DocumentationTableModel theModel, DocumentationTableView theView) {
    model = theModel;
    view = theView;
  }

  /**
   * Initializes model and view
   */
  public void init(boolean isColored) {
    model.addRows();
    view.getTable().setModel(model);
    if(isColored) {
      for(int i = 0; i < view.getTable().getColumnModel().getColumnCount(); i++) {
        System.out.println(i);
        view.getTable().getColumnModel().getColumn(i).setCellRenderer(new ColoredCellRenderer());
      }
    }
    view.getTable().setShowGrid(true);
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
    if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      JOptionPane message = new JOptionPane();
      try {
        FileWriter fw = new FileWriter(fc.getSelectedFile());
        fw.write(model.getCSV());
        message.showMessageDialog(view, "Success", ":)", JOptionPane.INFORMATION_MESSAGE);
        fw.close();
      } catch (IOException e) {
        message.showMessageDialog(view, "File Write Error!", "Error!", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  protected class ColoredCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      cellComponent.setBackground(model.getCellColor(row, column));
      return cellComponent;
    }
  }
}
