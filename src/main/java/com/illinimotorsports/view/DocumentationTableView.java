package com.illinimotorsports.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * View for documentation window
 */
public class DocumentationTableView extends JFrame {
  private JTable table;

  /**
   * Constructor for documentation view
   * All columns will be proper width
   */
  public DocumentationTableView() {
    table = new JTable(){
      @Override
      public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);
        int rendererWidth = component.getPreferredSize().width;
        TableColumn tableColumn = getColumnModel().getColumn(column);
        tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
        return component;
      }
    };
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    add(new JScrollPane(table));
  }

  /**
   * main init function
   */
  public void init() {
    pack();
    setVisible(true);
  }

  public JTable getTable() {
    return table;
  }
}
