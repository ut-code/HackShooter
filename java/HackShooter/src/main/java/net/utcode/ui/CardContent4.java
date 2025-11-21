package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent4 extends JPanel {
    public CardContent4(){
        super();
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド4.png")).getImage().getScaledInstance(height * 32 / 25, height * 18 / 25, Image.SCALE_SMOOTH)));
        add(label);
    }
}
