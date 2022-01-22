package me.anton.sickcore.api.utils.common.math;

import java.util.Random;

public class Randoms {

    public static int getRandomNumberInRange(int min, int max){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
