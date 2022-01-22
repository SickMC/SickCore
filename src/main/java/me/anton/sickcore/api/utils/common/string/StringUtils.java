package me.anton.sickcore.api.utils.common.string;

public class StringUtils {

    public static String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
