package com.illinimotorsports.view;

import javax.swing.*;

public class DocumentationTableView extends JFrame {
  private JTable table;

  public DocumentationTableView() {
    table = new JTable();
    add(new JScrollPane(table));
  }

  public void init() {
    pack();
    setVisible(true);
  }

  public JTable getTable() {
    return table;
  }
}
