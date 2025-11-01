package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class HackPanel extends MyGridPanel {

    JPanel cardPanel;
    CardLayout cardLayout;
    CardContent2 cardContent2;

    public HackPanel() {
        super(10, 10);
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardContent2 = new CardContent2();
        cardPanel.add(new CardContent1(), "Content1");
        cardPanel.add(cardContent2, "Content2");
        add(cardPanel, 0, 9, 0, 8);
        JButton next = new JButton(">");
        next.addActionListener(e -> {
            cardLayout.next(cardPanel);
        });
        add(next, 6, 7, 9, 9);
        JButton prev = new JButton("<");
        prev.addActionListener(e -> {
            cardLayout.previous(cardPanel);
        });
        add(prev, 2, 3, 9, 9);
    }

    public void dispose(){
        cardContent2.dispose();
    }
}
