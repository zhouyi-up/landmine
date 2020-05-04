package com.jtools.landmine.view;

import com.jtools.landmine.utils.RandomUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author corele
 */
public class ContentView extends JFrame{

    private List<BoxView> boxViewList;

    private int x,y,mineCount,flagCount;

    private JLabel clickLabel;
    private JLabel mineLabel;
    private JLabel flagLabel;

    public ContentView(List<BoxView> boxViewList,int x,int y,int mineCount,int flagCount){
        this.boxViewList = boxViewList;
        this.x = x;
        this.y = y;
        this.mineCount = mineCount;
        this.flagCount = flagCount;
        initContent();
    }

    private void initContent() {

        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(0,3,1,1));
        clickLabel= new JLabel("0");
        titlePanel.add(clickLabel);

        mineLabel = new JLabel(String.valueOf(mineCount));
        titlePanel.add(mineLabel);

        flagLabel = new JLabel(String.valueOf(flagCount));
        titlePanel.add(flagLabel);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(x,y,1,1));
        boxViewList.forEach(e->contentPanel.add(e));

        add(titlePanel,BorderLayout.NORTH);
        add(contentPanel,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(x*30,y*30);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setClickCount(int clickCount){
        clickLabel.setText(String.valueOf(clickCount));
    }

    public void setFlagCount(int flagCount){
        this.flagCount = flagCount;
        flagLabel.setText(String.valueOf(flagCount));
    }


    public void clickEmptyList(List<RandomUtils.Location> locations){
        for (RandomUtils.Location location : locations) {
            boxViewList.forEach(boxView -> {
                if (boxView.getLocationX() == location.getX() && boxView.getLocationY() == location.getY()){
                    boxView.clickEmpty();
                }
            });
        }
    }
}
