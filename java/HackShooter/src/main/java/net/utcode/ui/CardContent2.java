package net.utcode.ui;

import javax.swing.*;
import java.io.IOException;

public class CardContent2 extends JPanel {
    public CardContent2(){
        super();
        JButton button = new JButton("エクスプローラーを開く");
        button.addActionListener(e -> {
            String[] command = {"explorer", "C:\\Users\\" + System.getProperty("user.name") + "\\HackShooter"};
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(button);
    }
}
