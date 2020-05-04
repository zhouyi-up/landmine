package com.jtools.landmine;

import com.jtools.landmine.model.BoxModel;
import com.jtools.landmine.model.MineModel;
import com.jtools.landmine.utils.RandomUtils;
import com.jtools.landmine.view.BoxView;
import com.jtools.landmine.view.ContentView;
import com.jtools.landmine.view.GameEndView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author corele
 */
public class MineController {

    private int[][] mineArray;
    private Map<String, BoxModel> mineMap = new HashMap<>();
    private Map<String, BoxView> boxViewHashMap = new HashMap<>();
    private List<BoxView> boxViewList = new ArrayList<>();
    private Set<String> emptySet = new HashSet<>();

    private Set<String> flagSet = new HashSet<>();

    private int mineX,mineY,mine;

    private ContentView contentView;
    private GameEndView gameEndView;

    private int clickCount = 0;
    private int flagCount = 0;
    private long startTime;


    private MineController(int mineX,int mineY,int mine){
        this.mineX = mineX;
        this.mineY = mineY;
        this.mine = mine;

        init(mineX, mineY, mine);
    }

    private void init(int mineX, int mineY, int mine) {
        flagCount = mine;
        startTime =System.currentTimeMillis();

        mineArray = new int[mineX][mineY];

        for (int[] ints : mineArray) {
            for (int anInt : ints) {
                anInt = 0;
            }
        }

        Set<RandomUtils.Location> locationSet = RandomUtils.random(mineX, mineY, mine);
        locationSet.forEach(location -> mineArray[location.getX()][location.getY()] = 1);

        for (int i = 0; i < mineArray.length; i++) {
            for (int j = 0; j < mineArray[i].length; j++) {
                BoxModel boxModel = new BoxModel();
                boxModel.setX(i);
                boxModel.setY(j);
                boxModel.setMine(isMine(i,j));
                boxModel.setNearMineCount(findNearMineCount(i,j));
                String key = i+"-"+j;
                mineMap.put(key,boxModel);

                BoxView boxView = new BoxView(i, j, getMouseListener());
                boxView.setToolTipText(key);

                boxViewList.add(boxView);
                boxViewHashMap.put(key,boxView);
            }
        }

        contentView = new ContentView(boxViewList,mineX,mineY,mine,flagCount);
    }

    private MouseListener getMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BoxView box = (BoxView) e.getComponent();
                int nowX = box.getLocationX();
                int nowY = box.getLocationY();
                String key = nowX +"-"+ nowY;

                int button = e.getButton();
                if (button == 1){
                    if (box.isClick()){
                        return;
                    }
                    clickCount();

                    BoxModel boxInfo = mineMap.get(key);
                    if (boxInfo.isMine()){
                        mineAll();
                        gameOver();
                        return;
                    }else {
                        if (boxInfo.getNearMineCount() != 0){
                            boxViewHashMap.get(key).setNearMineCount(boxInfo.getNearMineCount());
                            return;
                        }
                        box.clickEmpty();
                        toEmpty(nowX, nowY);

                    }
                }

                if (button == 3){

                    boolean b = box.setFlag();
                    if (b){
                        clickCount();
                        flagCount();
                    }

                    BoxModel boxModel = mineMap.get(key);
                    if (boxModel.isMine()){
                        flagSet.add(key);
                    }
                    if (flagSet.size() == mine && flagCount == 0){
                        gameEnd();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                BoxView box = (BoxView) e.getComponent();
                box.mouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BoxView box = (BoxView) e.getComponent();
                box.mouseExited();
            }
        };
    }

    public void gameEnd(){

    }

    public void clickCount(){
        clickCount ++;
        contentView.setClickCount(clickCount);
    }

    public void flagCount(){
        flagCount --;
        contentView.setFlagCount(flagCount);
    }

    private List<String> empty(int nowX, int nowY) {
        List<String> keyList = new ArrayList<>();
        String key1 = (nowX -1) + "-" + (nowY -1);
        String key3 = (nowX -1) + "-" + nowY;
        String key8 = (nowX +1) + "-" + (nowY -1);
        String key6 = (nowX +1) + "-" + nowY;
        String key2 = nowX + "-" + (nowY -1);
        String key5 = nowX + "-" + (nowY +1);
        String key4 = (nowX +1) + "-" + (nowY +1);
        String key7 = (nowX -1) + "-" + (nowY +1);
        keyList.add(key1);
        keyList.add(key2);
        keyList.add(key3);
        keyList.add(key4);
        keyList.add(key5);
        keyList.add(key6);
        keyList.add(key7);
        keyList.add(key8);
        return keyList;
    }

    public void toEmpty(int x,int y){
        List<String> empty = empty(x, y);
        for (String key : empty) {
            BoxModel boxModel = mineMap.get(key);
            if (boxModel == null){
                continue;
            }
            if (boxModel.getNearMineCount() == 0){
                boxViewHashMap.get(key).clickEmpty();
                boolean add = emptySet.add(key);
                if (add){
                    toEmpty(boxModel.getX(), boxModel.getY());
                }
            }else {
                boxViewHashMap.get(key).setNearMineCount(boxModel.getNearMineCount());
            }
        }

    }

    public int findNearMineCount(int x,int y){
        int mineCount = 0;
        int startX = x - 1;
        int startY = y - 1;

        int rows = 3;
        int cols = 3;

        if (x - 1 < 0){
            startX = 0;
            rows = 2;
        }
        if (y -1 < 0){
            startY = 0;
            cols =2;
        }
        if ( x + 1 >= mineX){
            rows = 2;
        }
        if ( y + 1 >= mineY){
            cols = 2;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isMine(startX+i,startY+j)){
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public boolean isMine(int x,int y){
        return mineArray[x][y] == 1;
    }

    public void mineAll(){
        for (BoxView boxView : boxViewList) {
            boolean mine = mineMap.get(boxView.getLocationX()+"-"+boxView.getLocationY()).isMine();
            if (mine){
                boxView.clickMine();
            }
        }
    }

    public void clickEmptyForList(List<RandomUtils.Location> locations){
        contentView.clickEmptyList(locations);
    }

    public void gameOver(){
        long time = System.currentTimeMillis() - startTime;
        long s = time / 1000;

        gameEndView = new GameEndView(clickCount, String.valueOf(s)+"秒", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentView.dispose();
                contentView = null;
                mineMap.clear();
                boxViewHashMap.clear();
                boxViewList.clear();
                flagSet.clear();
                mineArray = null;
                emptySet.clear();
                init(mineX, mineY, mine);
                gameEndView.dispose();
            }
        });
    }

    public static void main(String[] args) {
        new MineController(30,30,100);
//        new GameEndView(11,"222");
    }
}
