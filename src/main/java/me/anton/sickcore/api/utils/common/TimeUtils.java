package me.anton.sickcore.api.utils.common;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String getTime(long time){
        if(time == -1 || time == 0) return "Permanent";
        int days = 0;
        while(time>86400000){
            days++;
            time-=86400000;
        }
        int hours = 0;
        while(time>3600000){
            hours++;
            time-=3600000;
        }
        int minutes = 0;
        while(time>60000){
            minutes++;
            time-=60000;
        }
        int seconds = 0;
        while (time>1000){
            seconds++;
            time-=1000;
        }

        StringBuilder builder = new StringBuilder();
        if(days != 0){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(days).append(" Tage");
        }
        if(hours != 0 && days<=7){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(hours).append(" Stunden");
        }
        if(minutes != 0 && days<=0){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(minutes).append(" Minuten");
        }
        if(seconds != 0 && days<=0){
            if(!builder.toString().isEmpty()) builder.append(" ");
            builder.append(seconds).append(" Sekunden");
        }
        return builder.toString().isEmpty() ? "Fehler bei Berechnung" : builder.toString();
    }

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
