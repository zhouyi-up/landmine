package com.jtools.landmine;

import com.jtools.landmine.model.MineModel;
import com.jtools.landmine.utils.RandomUtils;
import com.jtools.landmine.view.ContentView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author corele
 */
public class MineContext {

    private MineModel mineModel;
    private MineController mineController;
    Set<RandomUtils.Location> calcList = new HashSet<>();
    List<RandomUtils.Location> locationList = new ArrayList<>();

    public boolean isMine(int x,int y){
        return mineModel.getMineArr()[x][y] == 1;
    }

    public void mineAll(){
        mineController.mineAll();
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
        if (y -1 < 0 || y + 1 > mineModel.getY()){
            startY = 0;
            cols =2;
        }
        if ( x + 1 >= mineModel.getX()){
            rows = 2;
        }
        if ( y + 1 >= mineModel.getY()){
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

    public List<RandomUtils.Location> findNearIsZero(int x,int y){
        if (findNearMineCount(x,y) == 0){
            RandomUtils.Location location = new RandomUtils.Location();
            location.setX(x);
            location.setY(y);
            locationList.add(location);

            int startX = x - 1;
            int startY = y - 1;
            int rows = 3;
            int cols = 3;

            if (x - 1 < 0){
                startX = 0;
                rows = 2;
            }
            if (y -1 < 0 || y + 1 > mineModel.getY()){
                startY = 0;
                cols =2;
            }
            if ( x + 1 >= mineModel.getX()){
                rows = 2;
            }
            if ( y + 1 >= mineModel.getY()){
                cols = 2;
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int x1 = startX + i;
                    int y1 = startY + j;
                    if (findNearMineCount(x1, y1) == 0){
                        RandomUtils.Location location1 = new RandomUtils.Location();
                        location1.setX(x1);
                        location1.setY(y1);
                        boolean add = calcList.add(location1);
                        if (add){
                            findNearIsZero(x1, y1);
                        }

                    }
                }
            }
        }
        return locationList;
    }

    public void setEmpty(List<RandomUtils.Location> locations){
        this.mineController.clickEmptyForList(locations);
    }

    public MineModel getMineModel() {
        return mineModel;
    }

    public void setMineModel(MineModel mineModel) {
        this.mineModel = mineModel;
    }

    public MineController getMineController() {
        return mineController;
    }

    public void setMineController(MineController mineController) {
        this.mineController = mineController;
    }
}
