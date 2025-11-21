package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class HackFrame extends JFrame {
    HackPanel hackPanel;
    public HackFrame() {
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        setSize(height * 16 / 11, height * 10 / 11);
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
