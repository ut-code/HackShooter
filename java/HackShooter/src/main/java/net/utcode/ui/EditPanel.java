package net.utcode.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EditPanel extends JPanel {
    JScrollPane jScrollPane;
    JTextArea jTextArea;
    UndoManager undoManager;

    Runnable onChangeListener;
    Runnable onSaveListener;

    public EditPanel(){
        super();
        onChangeListener = null;
        undoManager = new UndoManager();
        setLayout(new BorderLayout());
        jTextArea = new JTextArea();
        jTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        jTextArea.setText(readFromFile());
        jTextArea.setCaretPosition(0);
        jTextArea.revalidate();
        jTextArea.repaint();
        jTextArea.getDocument().addUndoableEditListener(undoManager);
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(onChangeListener != null) onChangeListener.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(onChangeListener != null) onChangeListener.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        jScrollPane = new JScrollPane(jTextArea);
        add(jScrollPane, BorderLayout.CENTER);
    }

    public void setOnChangeListener(Runnable r){
        onChangeListener = r;
    }

    public void setOnSaveListener(Runnable r){
        onSaveListener = r;
    }

    public void undo(){
        if(undoManager.canUndo()) undoManager.undo();
    }

    public void redo(){
        if(undoManager.canRedo()) undoManager.redo();
    }

    public void reset(){
        jTextArea.setText(readFromFile());
        jTextArea.setCaretPosition(0);
    }

    public String readFromFile(){
        StringBuilder sb = new StringBuilder();
        try{
            Path path = Paths.get("C:\\Users\\" + System.getProperty("user.name") + "\\HackShooter\\index.html");
            List<String> lines = Files.readAllLines(path);
            for(int i = 0; i < lines.size(); i++){
                sb.append(lines.get(i));
                if(i != lines.size() - 1) sb.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public void saveFile(){
        FileWriter fw = null;
        try{
            fw = new FileWriter("C:\\Users\\" + System.getProperty("user.name") + "\\HackShooter\\index.html");
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            pw.print(jTextArea.getText());
            pw.close();
            onSaveListener.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(fw != null) try{fw.close();} catch (IOException e) {e.printStackTrace();};
        }
    }
}
