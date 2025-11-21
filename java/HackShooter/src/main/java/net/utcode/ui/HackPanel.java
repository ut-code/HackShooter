package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class HackPanel extends MyGridPanel {

    JPanel cardPanel;
    CardLayout cardLayout;
    CardContent2 cardContent2;

    private int pageNum = 1;

    public HackPanel() {
        super(10, 10);
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardContent2 = new CardContent2();
        cardPanel.add(new CardContent1(), "Content1");
        cardPanel.add(cardContent2, "Content2");
        cardPanel.add(new CardContent3(), "Content3");
        cardPanel.add(new CardContent4(), "Content4");
        cardPanel.add(new CardContent5(), "Content5");
        cardPanel.add(new CardContent6(), "Content6");
        add(cardPanel, 0, 9, 0, 8);
        JButton next = new JButton(">");
        next.addActionListener(e -> {
            if(pageNum < 6) {
                pageNum++;
                cardLayout.next(cardPanel);
            }
        });
        add(next, 6, 7, 9, 9);
        JButton prev = new JButton("<");
        prev.addActionListener(e -> {
            if(pageNum > 1) {
                pageNum--;
                cardLayout.previous(cardPanel);
            }
        });
        add(prev, 2, 3, 9, 9);
    }

    public void dispose(){
        cardContent2.dispose();
    }
}
