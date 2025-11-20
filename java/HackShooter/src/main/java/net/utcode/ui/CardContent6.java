package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent6 extends JPanel {
    public CardContent6(){
        super();
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド6.png")).getImage().getScaledInstance(1200, 675, Image.SCALE_SMOOTH)));
        add(label);
    }
}
