package net.utcode.ui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditFrame extends JFrame{
    EditPanel editPanel;
    public EditFrame(){
        super();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("コードを編集");
        setSize(900, 600);
        editPanel = new EditPanel();
        editPanel.setOnChangeListener(() -> {
            setTitle("コードを編集(保存されていません)");
        });
        editPanel.setOnSaveListener(() -> {
            setTitle("コードを編集");
        });
        add(editPanel);
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu1 = new JMenu("ファイル");
        JMenuItem item1_1 = new JMenuItem("保存");
        item1_1.addActionListener((e) -> {
            editPanel.saveFile();
        });
        item1_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu1.add(item1_1);
        JMenu menu2 = new JMenu("編集");
        JMenuItem item2_1 = new JMenuItem("元に戻す");
        JMenuItem item2_2 = new JMenuItem("やり直し");
        item2_1.addActionListener((e) -> {
            editPanel.undo();
        });
        item2_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        item2_2.addActionListener((e) -> {
            editPanel.redo();
        });
        item2_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        menu2.add(item2_1);
        menu2.add(item2_2);
        jMenuBar.add(menu1);
        jMenuBar.add(menu2);
        setJMenuBar(jMenuBar);
    }
}
