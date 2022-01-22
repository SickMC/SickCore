package me.anton.sickcore.api.utils.minecraft.bukkit.player;

import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class TitleBuilder {

    private String title;
    private String subtitle;
    private int fadeIn;
    private int fadeOut;
    private int stay;
    private String current;
    private static int taskID = 0;

    public TitleBuilder(String title, String subTitle, int fadeIn, int stay, int fadeOut){
        this.title = title;
        this.subtitle = subTitle;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        this.stay = stay;
    }

    public void sendTitle(Player player, JavaPlugin plugin){
        Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));

        player.showTitle(titlebuilder);
    }

    public void sendAnimatedTitle(Player player, JavaPlugin plugin){
        String[] args = title.split("");
        String[] subArgs = subtitle.split("");
        int length = args.length;
        int subLength = subArgs.length;
        AtomicInteger count = new AtomicInteger(0);
        this.current = "";
        taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (count.get() == length){
                Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                        , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));
                player.showTitle(titlebuilder);
                new SoundBuilder(Sound.ENTITY_PLAYER_LEVELUP, net.kyori.adventure.sound.Sound.Source.AMBIENT, 1, 1).play(player);
                stop();
            }
            new SoundBuilder(Sound.BLOCK_NOTE_BLOCK_PLING, net.kyori.adventure.sound.Sound.Source.AMBIENT, 2,1).play(player);

            if(isColorCode(args[count.get()])){
                current += args[count.get()] + args[count.get()+1];
                count.set(count.get()+2);
            }else{
                current += args[count.get()];
                count.set(count.get()+1);
            }
            new TitleBuilder(current, null, 0,10,0);
        },0,2).getTaskId();
    }

    public void sendCustomAnimatedTitle(Player player, int speed, SoundBuilder finishSound, JavaPlugin plugin){
        String[] args = title.split("");
        String[] subArgs = subtitle.split("");
        int length = args.length;
        int subLength = subArgs.length;
        AtomicInteger count = new AtomicInteger(0);
        this.current = "";
        int taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (count.get() == length){
                Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                        , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));
                player.showTitle(titlebuilder);
                finishSound.play(player);
                stop();
            }
            new SoundBuilder(Sound.BLOCK_NOTE_BLOCK_PLING, net.kyori.adventure.sound.Sound.Source.AMBIENT, 2,1).play(player);

            if(isColorCode(args[count.get()])){
                current += args[count.get()] + args[count.get()+1];
                count.set(count.get()+2);
            }else{
                current += args[count.get()];
                count.set(count.get()+1);
            }
            new TitleBuilder(current, null, 0,10,0);
        },0,speed).getTaskId();
    }

    private boolean isColorCode(String s){
        return s.equals("&") ||s.equals("§");
    }

    private void stop(){
        if(taskID == 0) return;
        Bukkit.getScheduler().cancelTask(taskID);
    }

}