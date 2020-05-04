package com.jtools.landmine.model;

/**
 * @author corele
 */
public class MineModel {

    private int x;
    private int y;
    private int[][] mineArr;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getMineArr() {
        return mineArr;
    }

    public void setMineArr(int[][] mineArr) {
        this.mineArr = mineArr;
    }
}
