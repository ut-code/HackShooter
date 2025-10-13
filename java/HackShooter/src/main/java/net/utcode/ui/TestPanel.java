package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel {
    TestPanel(){
        super();
        setBackground(new Color(0xf9f9f9));
        JLabel label = new JLabel("This window is written in Java!");
        label.setFont(new Font("SansSerif", Font.PLAIN, 40));
        add(label);
        JButton nativeButton = new JButton("This button calls native MessageBox");
        nativeButton.setFocusPainted(false);
        nativeButton.setFont(new Font("SansSerif", Font.ITALIC, 20));
        nativeButton.setBackground(new Color(0xf0f0f0));
        nativeButton.addActionListener(new TestListener());
        add(nativeButton);
        JButton swingButton = new JButton("This button calls swing MessageDialog");
        swingButton.setFont(new Font("SansSerif", Font.ITALIC, 20));
        swingButton.setBackground(new Color(0xf0f0f0));
        swingButton.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, "これはJavaから呼ばれています。", "success", JOptionPane.INFORMATION_MESSAGE);
        });
        add(swingButton);
    }
}
