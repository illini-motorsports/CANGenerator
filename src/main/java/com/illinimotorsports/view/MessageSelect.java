package com.illinimotorsports.view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * View to be used to select messages from CAN Spec
 */
public class MessageSelect {

  private static class CheckListModel extends AbstractTableModel {

    List<String> rows;

    public CheckListModel(List<String> messages) {
      rows = new ArrayList<String>(messages);
    }

    public int getRowCount() {
      return rows.size();
    }

    public int getColumnCount() {
      return 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
      return rows.get(rowIndex);
    }
  }
}
