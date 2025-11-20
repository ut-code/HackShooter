package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class CardContent3 extends JPanel {
    public CardContent3(){
        super();
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド3.png")).getImage().getScaledInstance(1200, 675, Image.SCALE_SMOOTH)));
        add(label);
    }
}
