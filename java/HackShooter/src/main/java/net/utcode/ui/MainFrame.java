package net.utcode.ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 675);
        setResizable(false);
        setVisible(true);
        add(new MainPanel());
        repaint();
    }
}
