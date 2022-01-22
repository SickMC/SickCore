package me.anton.sickcore.api.utils.common.math;

public class MathUtils {

    public static double round(double d) {
        return Math.round( d * 100 ) / 100.0;
    }

}
