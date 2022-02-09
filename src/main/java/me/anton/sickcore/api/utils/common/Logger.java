package me.anton.sickcore.api.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Logger {

    @Setter
    @Getter
    private static Level level = Level.ERROR;

    public static void debug(String message){
        log(message, Level.DEBUG);
    }

    public static void error(String message){
        log(message, Level.ERROR);
    }

    public static void info(String message){
        log(message, Level.INFO);
    }

    public static void warn(String message){
        log(message, Level.WARN);
    }

    public static void log(String message, Level level){
        if(level.getPriority() < level.getPriority()) return;
        System.out.println(level.getPrefix() + message);
    }

    @AllArgsConstructor
    @Getter
    public enum Level{

        ERROR("[ERROR] ", 0),
        WARN("[WARN] ", 1),
        INFO("[INFO] ", 2),
        DEBUG("[DEBUG] ", 3);

        private String prefix;
        private int priority;
    }
}
