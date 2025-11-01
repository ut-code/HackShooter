package net.utcode.ui;

import javax.swing.*;
import java.io.IOException;

public class CardContent2 extends JPanel {
    EditFrame editFrame;

    public CardContent2(){
        super();
        editFrame = new EditFrame();
        JButton button = new JButton("テキストエディタを開く");
        button.addActionListener(e -> {
            editFrame.setVisible(true);
        });
        add(button);
    }

    public void dispose(){
        editFrame.dispose();
    }
}
