package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent4 extends JPanel {
    public CardContent4(){
        super();
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド4.png")).getImage().getScaledInstance(1200, 675, Image.SCALE_SMOOTH)));
        add(label);
    }
}
