package com.jtools.landmine.view;

import com.jtools.landmine.MineContext;
import com.jtools.landmine.utils.RandomUtils;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * @author corele
 */
public class BoxView extends JPanel {


    private final static Color default_color = new Color(220,220,220);
    private final static Color click_color = new Color(250,240,230);

    private int locationX;
    private int locationY;

    private final static int width = 30;
    private final static int height = 30;

    private JLabel mine;
    private boolean isClick = false;
    private boolean isFlag = false;


    public BoxView(int locationX,int locationY,MouseListener mouseListener){
        init(locationX,locationY,default_color);
        addMouseListener(mouseListener);
    }

    private void init(int locationX, int locationY ,Color color) {
        this.locationX = locationX;
        this.locationY = locationY;

        mine = new JLabel();
        add(mine);
        setName("box-"+locationX+"-"+locationY);
        setBackground(color);
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void clickMine(){
        isClick = true;
        setBackground(Color.RED);
    }

    public void clickEmpty(){
        isClick = true;
        setBackground(click_color);
    }

    public void setNearMineCount(int mineCount){
        isClick = true;
        mine.setText(String.valueOf(mineCount));
        setBackground(click_color);
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public void mouseEntered() {
        if (!isClick && !isFlag){
            setBackground(click_color);
        }
    }

    public void mouseExited() {
        if (!isClick && !isFlag){
            setBackground(default_color);
        }
    }

    public boolean setFlag(){
        if (!isClick){
            isFlag = true;
            setBackground(Color.cyan);
            return true;
        }
        return false;
    }
}
