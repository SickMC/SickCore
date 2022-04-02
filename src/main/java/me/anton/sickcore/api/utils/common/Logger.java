package me.anton.sickcore.api.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.ProxyCore;
import me.anton.sickcore.core.Core;

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
        switch (Core.getInstance().getEnvironment()){
            case BUKKIT -> {
                switch (level){
                    case INFO -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.INFO, message);
                    case WARN -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.WARNING, message);
                    case DEBUG -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.FINE, message);
                    case ERROR -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.SEVERE, message);
                }
            }
            case VELOCITY -> {
                switch (level){
                    case INFO -> ProxyCore.getInstance().getBootstrap().getLogger().info(message);
                    case WARN -> ProxyCore.getInstance().getBootstrap().getLogger().warn(message);
                    case DEBUG -> ProxyCore.getInstance().getBootstrap().getLogger().debug(message);
                    case ERROR -> ProxyCore.getInstance().getBootstrap().getLogger().error(message);
                }
            }
        }
    }

    public static void log(String message, Level level, Class clazz){
        if(level.getPriority() < level.getPriority()) return;
        switch (Core.getInstance().getEnvironment()){
            case BUKKIT -> {
                switch (level){
                    case INFO -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.INFO,"(" + clazz.getSimpleName() + ")" +  message);
                    case WARN -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.WARNING,"(" + clazz.getSimpleName() + ")" +  message);
                    case DEBUG -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.FINE,"(" + clazz.getSimpleName() + ")" +  message);
                    case ERROR -> BukkitCore.getInstance().getPlugin().getLogger().log(java.util.logging.Level.SEVERE,"(" + clazz.getSimpleName() + ")" +  message);
                }
            }
            case VELOCITY -> {
                switch (level){
                    case INFO ->  ProxyCore.getInstance().getBootstrap().getLogger().info("(" + clazz.getSimpleName() + ")" + message);
                    case WARN ->  ProxyCore.getInstance().getBootstrap().getLogger().warn("(" + clazz.getSimpleName() + ")" +  message);
                    case DEBUG ->  ProxyCore.getInstance().getBootstrap().getLogger().debug("(" + clazz.getSimpleName() + ")" +  message);
                    case ERROR ->  ProxyCore.getInstance().getBootstrap().getLogger().error("(" + clazz.getSimpleName() + ")" +  message);
                }
            }
        }
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
