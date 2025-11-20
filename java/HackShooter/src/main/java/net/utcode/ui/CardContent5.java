package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent5 extends JPanel {
    public CardContent5(){
        super();
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド5.png")).getImage().getScaledInstance(1200, 675, Image.SCALE_SMOOTH)));
        add(label);
    }
}
