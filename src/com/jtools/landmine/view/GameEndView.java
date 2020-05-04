package com.jtools.landmine.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * @author corele
 */
public class GameEndView extends JFrame {

    private JLabel clinkCountLabel;
    private JLabel timeLabel;
    private JButton restart;

    public GameEndView(int clickCount, String time, ActionListener actionListener) throws HeadlessException {
        setLayout(new BorderLayout());

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3,2,1,1));

        jPanel.add(new JLabel("点击："));
        clinkCountLabel = new JLabel(clickCount+" 次");
        jPanel.add(clinkCountLabel);

        jPanel.add(new JLabel("时长："));
        timeLabel = new JLabel(time);
        jPanel.add(timeLabel);

        restart = new JButton("重新开始");
        restart.addActionListener(actionListener);

        add(jPanel,BorderLayout.CENTER);
        add(restart,BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setSize(300,200);
        setVisible(true);
    }
}
