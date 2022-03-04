package me.anton.sickcore.api.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Logger {

    @Setter
    @Getter
    private static Level level = Level.ERROR;

    public static void debug(String message, Class clazz){
        log(message, Level.DEBUG, clazz);
    }

    public static void error(String message, Class clazz){
        log(message, Level.ERROR, clazz);
    }

    public static void info(String message, Class clazz){
        log(message, Level.INFO, clazz);
    }

    public static void warn(String message, Class clazz){
        log(message, Level.WARN, clazz);
    }

    public static void log(String message, Level level){
        if(level.getPriority() < level.getPriority()) return;
        System.out.println(level.getPrefix() + message);
    }

    public static void log(String message, Level level, Class clazz){
        if(level.getPriority() < level.getPriority()) return;
        System.out.println(level.getPrefix()+ "(" + clazz.getSimpleName() + ")" + message);
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
