package com.illinimotorsports.model;

import javax.swing.table.DefaultTableModel;

public class DocumentationTableModel extends DefaultTableModel {
  public DocumentationTableModel(String[] columns, String[][] content) {
    super(columns, 0);
    for(int i = 0; i < content.length; i++) {
      addRow(content[i]);
    }
  }
}
