package com.illinimotorsports.view;

import javax.swing.*;

public class MainView {

  JFrame viewFrame;
  ApplicationPanel appPanel;

  public MainView() {
    viewFrame = new JFrame("FSAE CAN Generator");
    viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    appPanel = new ApplicationPanel();
  }

  public void addAppPanel() {
    viewFrame.add(appPanel);
    viewFrame.pack();
    viewFrame.setVisible(true);
  }

  public ApplicationPanel getAppPanel() {
    return appPanel;
  }
}
