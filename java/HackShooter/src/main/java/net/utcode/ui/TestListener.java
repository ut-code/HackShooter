package net.utcode.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestListener implements ActionListener {
    static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");
    static final boolean IS_LINUX = System.getProperty("os.name").toLowerCase().startsWith("linux");
    static final boolean IS_MAC = System.getProperty("os.name").toLowerCase().startsWith("mac");

    @Override
    public void actionPerformed(ActionEvent e){
        if(IS_WINDOWS){
            messageBox();
        }
        else if(IS_LINUX){
            JOptionPane.showMessageDialog(null, "Linuxにはまだ対応していません。これはJavaから呼び出しています。", "怠慢", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(IS_MAC){
            JOptionPane.showMessageDialog(null, "Macにはまだ対応していません。これはJavaから呼び出しています。", "怠慢", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    native void messageBox();
}
