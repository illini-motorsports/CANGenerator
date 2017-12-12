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
  private JButton doneButton;
  private JButton saveButton;
  private JFileChooser fc;

  /**
   * Constructor for documentation view
   * All columns will be proper width
   */
  public DocumentationTableView() {
    doneButton = new JButton("Done");
    saveButton = new JButton("Save to CSV");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);
    buttonPanel.add(doneButton);

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
    fc = new JFileChooser();
    setLayout(new BorderLayout());
    add(new JScrollPane(table));
    add(buttonPanel, BorderLayout.SOUTH);
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

  public JButton getDoneButton() {
    return doneButton;
  }

  public JButton getSaveButton() {
    return saveButton;
  }

  public JFileChooser getFc() {
    return fc;
  }
}
