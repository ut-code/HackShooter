package net.utcode.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainPanel extends MyGridPanel {

    MainPanel(){
        super(4, 4);
        setPadding(10);
        setGaps(5, 5);
        setBackground(new Color(0xf9f9f9));
        JButton startGameButton = new JButton("ゲーム");
        startGameButton.setBackground(new Color(0xf78800));
        startGameButton.setForeground(new Color(0xffffff));
        startGameButton.setBorderPainted(false);
        startGameButton.setFont(new Font("SansSerif", Font.PLAIN, 80));
        startGameButton.setFocusPainted(false);
        startGameButton.addActionListener(e -> {
            String[] commands = {"cmd", "/c", "start", "msedge", "C:/Users/" + System.getProperty("user.name") + "/HackShooter/index.html"};
            try {
                Runtime.getRuntime().exec(commands);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(startGameButton, 0, 2, 0, 2);
        JButton startHackingButton = new JButton("改造");
        startHackingButton.setBackground(new Color(0xff0000));
        startHackingButton.setForeground(new Color(0xffffff));
        startHackingButton.setBorderPainted(false);
        startHackingButton.setFocusPainted(false);
        startHackingButton.setFont(new Font("SansSerif", Font.PLAIN, 60));
        add(startHackingButton, 3, 3, 0, 1);
        JButton resetButton = new JButton("リセット");
        resetButton.setBackground(new Color(0x0021ff));
        resetButton.setForeground(new Color(0xffffff));
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 35));
        add(resetButton, 3, 3, 2, 2);
    }
}
