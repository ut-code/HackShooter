package net.utcode.ui;

import javax.swing.*;

public class HackFrame extends JFrame {
    public HackFrame(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(900, 600);
        add(new HackPanel());
    }
}
