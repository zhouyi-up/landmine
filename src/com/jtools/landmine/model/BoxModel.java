package com.jtools.landmine.model;

/**
 * @author corele
 */
public class BoxModel {

    private boolean isMine;
    private int nearMineCount;
    private int x,y;

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNearMineCount() {
        return nearMineCount;
    }

    public void setNearMineCount(int nearMineCount) {
        this.nearMineCount = nearMineCount;
    }

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
}
