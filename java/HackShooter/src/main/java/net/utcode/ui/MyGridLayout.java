package net.utcode.ui;

import java.awt.*;
import java.util.ArrayList;

public class MyGridLayout implements LayoutManager {
    private Container container;
    private ArrayList<Component> components;
    private ArrayList<int[]> locations;
    private int numRows;
    private int numColumns;
    private int minW = 64;
    private int minH = 36;
    private int rowGap = 0;
    private int columnGap = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;
    private int paddingLeft = 0;
    private int paddingRight = 0;

    public MyGridLayout(int rows, int columns){
        this.numRows = rows;
        this.numColumns = columns;
        components = new ArrayList<Component>();
        locations = new ArrayList<int[]>();
    }

    public void setGaps(int rowGap, int columnGap){
        this.rowGap = rowGap;
        this.columnGap = columnGap;
    }

    public void setRowGap(int rowGap) {
        this.rowGap = rowGap;
    }

    public void setColumnGap(int columnGap){
        this.columnGap = columnGap;
    }

    public void setPadding(int padding) {
        this.paddingTop = padding;
        this.paddingBottom = padding;
        this.paddingRight = padding;
        this.paddingLeft = padding;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void addLocation(int left, int right, int top, int bottom){
        int[] location = {left, right, top, bottom};
        locations.add(location);
    }

    @Override
    public void addLayoutComponent(String name, Component comp){
        int index = components.size();
        if(index <= locations.size()){
            components.add(comp);
        }
        if(container != null) container.doLayout();
    }

    @Override
    public void removeLayoutComponent(Component comp){
        int index = components.indexOf(comp);
        components.remove(index);
        if(index < locations.size()) locations.remove(index);
        if(container != null) container.doLayout();
    }

    @Override
    public void layoutContainer(Container parent){
        if(components.size() > locations.size()) return;
        container = parent;
        double columnWidth = (double)(parent.getWidth() - paddingLeft - paddingRight - columnGap * (numColumns - 1)) / numColumns;
        double rowHeight = (double)(parent.getHeight() - paddingTop - paddingBottom - rowGap * (numRows - 1)) / numRows;
        for(int i = 0; i < components.size(); i++){
            components.get(i).setBounds(
                    (int)(paddingLeft + locations.get(i)[0] * (columnWidth + columnGap)),
                    (int)(paddingTop + locations.get(i)[2] * (rowHeight + rowGap)),
                    (int)((columnWidth + columnGap) * (locations.get(i)[1] - locations.get(i)[0] + 1) - columnGap),
                    (int)((rowHeight + rowGap) * (locations.get(i)[3] - locations.get(i)[2] + 1) - rowGap)
            );
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(minW, minH);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }
}
