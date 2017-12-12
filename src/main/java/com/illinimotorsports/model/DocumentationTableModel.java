package com.illinimotorsports.model;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Model for documentation window
 */
public class DocumentationTableModel extends DefaultTableModel {

  private static final Color[] colorPicker = {Color.PINK, Color.ORANGE, Color.YELLOW};
  private String[] columns;
  private List<String[]> data;
  private List<Color[]> dataColors;
  /**
   * adds given rows to internal model
   */
  public DocumentationTableModel(String[] tableColumns, List<String[]> tableData) {
    super(tableColumns, 0);
    this.columns = tableColumns;
    this.data = tableData;
    this.dataColors = populateColors();
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public void addRows() {
    for(String[] row: data) {
      addRow(row);
    }
  }

  public List<Color[]> populateColors() {
    List<Color[]> colors = new ArrayList<>();
    for(String[] row: data) {
      Color[] colorRow = new Color[columns.length];
      for(int i = 0; i < 4; i++) {
        colorRow[i] = Color.WHITE;
      }
      int colorPickeridx = 0;
      for(int i = 4; i < columns.length; i++) {
        if(row[i] == null) {
          colorRow[i] = Color.WHITE;
        } else if(row[i].equals("%")) {
          colorRow[i] = colorPicker[colorPickeridx];
        } else {
          colorPickeridx = (colorPickeridx + 1) % colorPicker.length;
          colorRow[i] = colorPicker[colorPickeridx];
        }
      }
      colors.add(colorRow);
    }
    return colors;
  }

  public String getCSV() {
    StringJoiner colsSJ = new StringJoiner(",");
    for(int i = 0; i < columns.length; i++) {
      colsSJ.add("\"" + columns[i] + "\"");
    }
    String dataCSV = "";
    for(String[] row: data) {
      dataCSV += dataToCSVRow(row);
    }
    return colsSJ + "\n" + dataCSV;
  }

  public String dataToCSVRow(String[] row) {
    StringJoiner rowSJ = new StringJoiner(",", "", "\n");
    for(int i = 0; i < row.length; i++) {
      rowSJ.add("\"" + row[i] + "\"");
    }
    return rowSJ.toString();
  }

  public Color getCellColor(int row, int col) {
    return dataColors.get(row)[col];
  }

  public String[] getColumns() {
    return columns;
  }
}
