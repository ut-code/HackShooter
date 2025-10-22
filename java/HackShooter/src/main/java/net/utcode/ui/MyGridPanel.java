package net.utcode.ui;

import javax.swing.*;
import java.awt.*;

public class MyGridPanel extends JPanel {
    MyGridLayout myGridLayout;

    MyGridPanel(){
        this(1, 1);
    }

    MyGridPanel(int numRows, int numColumns){
        super();
        myGridLayout = new MyGridLayout(numRows, numColumns);
        this.setLayout(myGridLayout);
    }

    public void setPadding(int padding){
        myGridLayout.setPadding(padding);
    }

    public void setPaddingTop(int paddingTop){
        myGridLayout.setPaddingTop(paddingTop);
    }

    public void setPaddingBottom(int paddingBottom){
        myGridLayout.setPaddingBottom(paddingBottom);
    }

    public void setPaddingLeft(int paddingLeft){
        myGridLayout.setPaddingLeft(paddingLeft);
    }

    public void setPaddingRight(int paddingRight){
        myGridLayout.setPaddingRight(paddingRight);
    }

    public void setColumnGap(int columnGap){
        myGridLayout.setColumnGap(columnGap);
    }

    public void setRowGap(int rowGap){
        myGridLayout.setRowGap(rowGap);
    }

    public void setGaps(int rowGap, int columnGap){
        myGridLayout.setGaps(rowGap, columnGap);
    }

    public Component add(Component comp, int left, int right, int top, int bottom){
        myGridLayout.addLocation(left, right, top, bottom);
        return super.add("", comp);
    }

    @Override
    public Component add(Component comp) {
        return null;
    }

    @Override
    public Component add(Component comp, int index) {
        return null;
    }

    @Override
    public Component add(String name, Component comp) {
        return null;
    }
}
