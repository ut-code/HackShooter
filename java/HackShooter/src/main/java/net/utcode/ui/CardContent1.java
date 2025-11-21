package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent1 extends JPanel {
    public CardContent1(){
        super();
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド1.png")).getImage().getScaledInstance(height * 32 / 25, height * 18 / 25, Image.SCALE_SMOOTH)));
        add(label);
    }
}
