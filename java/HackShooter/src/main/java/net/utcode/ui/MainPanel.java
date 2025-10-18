package net.utcode.ui;

import net.utcode.Initializer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainPanel extends MyGridPanel {

    JFrame hackFrame;

    MainPanel(){
        super(4, 4);
        hackFrame = new HackFrame();
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
            String[] commands = {"cmd", "/c", "start ", "msedge", "C:/Users/" + System.getProperty("user.name") + "/HackShooter/index.html"};
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
        startHackingButton.addActionListener(e -> {
            hackFrame.setVisible(true);
        });
        add(startHackingButton, 3, 3, 0, 1);
        JButton resetButton = new JButton("リセット");
        resetButton.setBackground(new Color(0x0021ff));
        resetButton.setForeground(new Color(0xffffff));
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 35));
        resetButton.addActionListener(e -> {
            int answer1 = JOptionPane.showConfirmDialog(this, "変更をリセットします。現在の変更は保存しましたか？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(answer1 == JOptionPane.YES_OPTION){
                int answer2 = JOptionPane.showConfirmDialog(this, "今までの変更は戻せません。いいですね？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(answer2 == JOptionPane.YES_OPTION){
                    int answer3 = JOptionPane.showConfirmDialog(this, "ほんとのほんとにいいですね？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(answer3 == JOptionPane.YES_OPTION){
                        if(Initializer.init()){
                            JOptionPane.showMessageDialog(this, "リセットしました");
                        }
                    }
                }
            }
        });
        add(resetButton, 3, 3, 2, 2);
    }
}
