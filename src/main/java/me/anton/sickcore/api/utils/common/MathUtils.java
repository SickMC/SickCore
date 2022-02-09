package me.anton.sickcore.api.utils.common;

import java.util.Random;

public class MathUtils {

    public static double round(double d) {
        return Math.round( d * 100 ) / 100.0;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    public static int getRandomNumberInRange(int min, int max){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
