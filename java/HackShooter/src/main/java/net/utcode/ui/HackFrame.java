package net.utcode.ui;

import javax.swing.*;

public class HackFrame extends JFrame {
    HackPanel hackPanel;
    public HackFrame() {
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(900, 600);
        hackPanel = new HackPanel();
        add(hackPanel);
    }

    @Override
    public void dispose(){
        hackPanel.dispose();
        super.dispose();
    }
}
