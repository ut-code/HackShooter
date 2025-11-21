package net.utcode.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CardContent2 extends JPanel {
    EditFrame editFrame;

    public CardContent2(){
        super();
        editFrame = new EditFrame();
        JButton button = new JButton("テキストエディタを開く");
        button.setSize(100, 40);
        button.addActionListener(e -> {
            editFrame.setVisible(true);
        });
        add(button);
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        JLabel label = new JLabel(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("slides/スライド2.png")).getImage().getScaledInstance(height * 32 / 25, height * 18 / 25, Image.SCALE_SMOOTH)));
        add(label);
    }

    public void dispose(){
        editFrame.dispose();
    }
}
