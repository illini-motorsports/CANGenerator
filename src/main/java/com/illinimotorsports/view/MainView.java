package com.illinimotorsports.view;

import javax.swing.*;

/**
 * Top level view class
 */
public class MainView {

  JFrame viewFrame;
  ApplicationPanel appPanel;

  /**
   * Initializes app panel and main view frame
   */
  public MainView() {
    viewFrame = new JFrame("FSAE CAN Generator");
    viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    appPanel = new ApplicationPanel();
  }

  /**
   * Sets app panel to be viewable
   */
  public void addAppPanel() {
    viewFrame.add(appPanel);
    viewFrame.pack();
    viewFrame.setVisible(true);
  }

  public void addPanel(JComponent component) {
    viewFrame.add(component);
    viewFrame.pack();
    viewFrame.setVisible(true);
  }

  public ApplicationPanel getAppPanel() {
    return appPanel;
  }
}
