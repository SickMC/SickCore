package me.anton.sickcore.api.utils.common.math;

import java.util.concurrent.TimeUnit;

public class TimeFormatting {

    public static String formatMillis(long duration) {
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - days * 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - hours * 60 - days * 24 * 60;

        if (days == 0 && hours == 0) {
            return minutes + "m";
        } else if (days == 0 && hours >= 1) {
            return hours + "h " + minutes + "m";
        }

        return days + "d " + hours + "h " + minutes + "m";
    }

}
