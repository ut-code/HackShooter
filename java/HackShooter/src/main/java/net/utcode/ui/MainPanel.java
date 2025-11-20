package net.utcode.ui;

import net.utcode.Initializer;
import net.utcode.webview.WebViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainPanel extends MyGridPanel {

    JFrame hackFrame;

    JLabel descriptionLabel;

    MainPanel(){
        super(20, 40);
        hackFrame = new HackFrame();
        setPadding(10);
        setPaddingLeft(0);
        setPaddingBottom(0);
        setGaps(0, 5);
        setBackground(new Color(0x4f0077));
        JLabel titleLabel = new JLabel("Hack Shooter");
        titleLabel.setFont(new Font(Font.DIALOG, Font.ITALIC | Font.BOLD, 90));
        titleLabel.setForeground(new Color(0xffe500));
        add(titleLabel, 0, 20, 0, 2);
        JLabel subtitleLabel = new JLabel("────ハッキングで無双────");
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        subtitleLabel.setForeground(new Color(0xffe500));
        add(subtitleLabel, 22, 38, 1, 2);
        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        descriptionLabel.setOpaque(true);
        descriptionLabel.setBackground(Color.BLACK);
        descriptionLabel.setForeground(Color.WHITE);
        add(descriptionLabel, 0, 24, 18, 19);
        JButton startGameButton = new JButton();
        startGameButton.setBorderPainted(false);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setFont(new Font("SansSerif", Font.PLAIN, 170));
        startGameButton.setFocusPainted(false);
        startGameButton.addActionListener(e -> {
            WebViewManager.createWindow();
        });
        startGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                descriptionLabel.setText("シューティングゲームを起動します。");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        startGameButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("buttons/start_game.png")).getImage().getScaledInstance(702, 406, Image.SCALE_SMOOTH)));
        startGameButton.setPressedIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("buttons/start_game_clicked.png")).getImage().getScaledInstance(702, 406, Image.SCALE_SMOOTH)));
        //このサイズ調整頭悪すぎてなんとかしたいけどswingの柔軟性終わってて大変そう
        //でもこれだとわざわざグリッドレイアウト作った意味ないもんな……
        add(startGameButton, 1, 24, 4, 16);
        JButton startHackingButton = new JButton();
        startHackingButton.setContentAreaFilled(false);
        startHackingButton.setBorderPainted(false);
        startHackingButton.setFocusPainted(false);
        startHackingButton.addActionListener(e -> {
            hackFrame.setVisible(true);
        });
        startHackingButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                descriptionLabel.setText("プログラミングでゲームをハッキングします。");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        startHackingButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("buttons/start_hacking.png")).getImage().getScaledInstance(378, 406, Image.SCALE_SMOOTH)));
        startHackingButton.setPressedIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("buttons/start_hacking_clicked.png")).getImage().getScaledInstance(378, 406, Image.SCALE_SMOOTH)));
        add(startHackingButton, 26, 38, 4, 16);
        JButton resetButton = new JButton("リセット");
        resetButton.setBackground(new Color(0xff3300));
        resetButton.setForeground(new Color(0xffffff));
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 30));
        resetButton.addActionListener(e -> {
            int answer1 = JOptionPane.showConfirmDialog(this, "変更をリセットします。", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(answer1 == JOptionPane.YES_OPTION){
                int answer2 = JOptionPane.showConfirmDialog(this, "今までの変更は戻せません。いいですね？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(answer2 == JOptionPane.YES_OPTION){
                    int answer3 = JOptionPane.showConfirmDialog(this, "ほんとのほんとにいいですね？", "確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(answer3 == JOptionPane.YES_OPTION){
                        Initializer.reset();
                        WebViewManager.destroyWindow();
                        hackFrame.dispose();
                        hackFrame = new HackFrame();
                        JOptionPane.showMessageDialog(this, "リセットしました");

                    }
                }
            }
        });
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                descriptionLabel.setText("ハッキングをリセットして元に戻します。");
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(resetButton, 31, 38, 18, 19);
    }
}
