package net.utcode.ui;

import javax.swing.*;

public class HackFrame extends JFrame {
    HackPanel hackPanel;
    public HackFrame() {
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1350, 900);
        setResizable(false);
        hackPanel = new HackPanel();
        add(hackPanel);
    }

    @Override
    public void dispose(){
        hackPanel.dispose();
        super.dispose();
    }
}
