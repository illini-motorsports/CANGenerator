package com.illinimotorsports.view;

import javax.swing.*;
import java.awt.*;

/**
 * View to be used to select messages from CAN Spec
 */
public class SelectView extends JFrame {

  private CheckBoxListView list;
  private JButton selectAllButton;
  private JButton deselectAllButton;
  private JButton submitButton;

  /**
   * Contains list area, and three buttons
   * @param checkBoxList
   */
  public SelectView(CheckBoxListView checkBoxList) {
    super();

    setLayout(new BorderLayout());

    list = checkBoxList;
    selectAllButton = new JButton("Select All");
    deselectAllButton = new JButton("Deselect All");
    submitButton = new JButton("Done");

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(selectAllButton);
    buttonPanel.add(deselectAllButton);
    buttonPanel.add(submitButton);

    this.add(new JScrollPane(list));
    this.add(buttonPanel, BorderLayout.SOUTH);
  }

  public void init() {
    this.pack();
    this.setVisible(true);
  }

  public CheckBoxListView getList() {
    return list;
  }

  public JButton getSelectAllButton() {
    return selectAllButton;
  }

  public JButton getDeselectAllButton() {
    return deselectAllButton;
  }

  public JButton getSubmitButton() {
    return submitButton;
  }
}
