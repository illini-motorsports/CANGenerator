package com.illinimotorsports;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JPanel implements ActionListener {

    public JButton openButton;
    JTextArea logArea;
    JFileChooser fileChooser;

    public MainWindow() {
        super(new BorderLayout());
        // Set up logging area
        logArea = new JTextArea(5,20);
        logArea.setMargin(new Insets(5,5,5,5));
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        // Set up File Chooser
        fileChooser = new JFileChooser();

        // Open Button
        openButton = new JButton("Open JSON");
        openButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FSAE CAN Toolbox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainWindow());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openButton) {
            int fileRet = fileChooser.showOpenDialog(MainWindow.this);
            if(fileRet == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                logArea.append("Selected " + file.getName());
            } else {
                logArea.append("User Canceled ");
            }
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
