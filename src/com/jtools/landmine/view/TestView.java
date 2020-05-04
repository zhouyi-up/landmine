package com.jtools.landmine.view;

import sun.awt.Graphics2Delegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * @author corele
 */
public class TestView extends JFrame implements MouseListener {

    private Graphics g;

    int x1, y1, x2, y2;

    public TestView() {
        JPanel jPanel = new JPanel();

        add(jPanel);
        setSize(500,500);
        setVisible(true);

        g = jPanel.getGraphics();
        jPanel.addMouseListener(this);
    }

    public static void main(String[] args) {
        new TestView();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        g.drawLine(x1, y1, x2, y2);
        x1 = x2 = y1 = y2 = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
