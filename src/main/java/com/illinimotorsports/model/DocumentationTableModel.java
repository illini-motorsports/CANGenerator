package com.illinimotorsports.model;

import javax.swing.table.DefaultTableModel;

/**
 * Model for documentation window
 */
public class DocumentationTableModel extends DefaultTableModel {
  /**
   * adds given rows to internal model
   * @param columns
   * @param content
   */
  public DocumentationTableModel(String[] columns, String[][] content) {
    super(columns, 0);
    for(int i = 0; i < content.length; i++) {
      addRow(content[i]);
    }
  }
}
