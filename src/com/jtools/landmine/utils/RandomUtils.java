package com.jtools.landmine.utils;

import java.util.*;

/**
 * @author corele
 */
public final class RandomUtils {


    public static Set<Location> random(int x,int y,int count){
        Set<Location> locationSet = new HashSet<>();

        int index = count;

        while (index > 0 ){
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (isMine()){
                        Location location = new Location();
                        location.setX(i);
                        location.setY(j);
                        locationSet.add(location);

                        index--;

                        if (index <= 0){
                            break;
                        }
                    }
                }
                if (index <= 0){
                    break;
                }
            }
            if (index <= 0){
                break;
            }
        }

        return locationSet;
    }

    public static boolean isMine(){
        Random random = new Random();
        int i = random.nextInt(500);
        return i <= 1;
    }

    public static void main(String[] args) {
        Set<Location> random = random(30, 30, 30);
        System.out.println(random.size());
        random.forEach(e-> System.out.println(e.getX()+"-"+e.getY()));

//        for (int i = 0; i < 10; i++) {
//            Random randomInt = new Random();
//            int j = randomInt.nextInt(10);
//            System.out.println(j);
//        }
    }

    public static class Location{
        private int x;
        private int y;

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

        @Override
        public boolean equals(Object obj) {
            Location location = (Location) obj;
            if (location.getX() == x && location.getY() == y){
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(String.valueOf(x) + String.valueOf(y));
        }
    }
}
