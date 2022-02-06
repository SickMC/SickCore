package me.anton.sickcore.api.utils.minecraft.bukkit.player;

import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import me.anton.sickcore.core.Core;
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

    public TitleBuilder(String title, String subtitle, int stay){
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = 2;
        this.stay = stay;
        this.fadeOut = 2;
    }

    public void sendTitle(Player player){
        Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));

        player.showTitle(titlebuilder);
    }

    public void sendAnimatedTitle(Player player){
        String[] args = title.split("");
        String[] subArgs = subtitle.split("");
        int length = args.length;
        AtomicInteger count = new AtomicInteger(0);
        this.current = "";
        taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(Core.getInstance().bukkit().getPlugin(), () -> {
            if (count.get() == length){
                Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                        , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));
                player.showTitle(titlebuilder);
                new SoundBuilder(Sound.ENTITY_PLAYER_LEVELUP, net.kyori.adventure.sound.Sound.Source.AMBIENT, 1, 1).play(player);
                stop();
            }
            new SoundBuilder(Sound.BLOCK_NOTE_BLOCK_PLING, net.kyori.adventure.sound.Sound.Source.AMBIENT, 2,1).play(player);

            new TitleBuilder(current, null, 0,10,0);
        },0,2).getTaskId();
    }

    public void sendCustomAnimatedTitle(Player player, int speed, SoundBuilder finishSound){
        String[] args = title.split("");
        String[] subArgs = subtitle.split("");
        int length = args.length;
        AtomicInteger count = new AtomicInteger(0);
        this.current = "";
        int taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(Core.getInstance().bukkit().getPlugin(), () -> {
            if (count.get() == length){
                Title titlebuilder = Title.title(Component.text(title), Component.text(subtitle)
                        , Title.Times.of(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut)));
                player.showTitle(titlebuilder);
                finishSound.play(player);
                stop();
            }
            new SoundBuilder(Sound.BLOCK_NOTE_BLOCK_PLING, net.kyori.adventure.sound.Sound.Source.AMBIENT, 2,1).play(player);
            new TitleBuilder(current, null, 0,10,0);
        },0,speed).getTaskId();
    }

    private void stop(){
        if(taskID == 0) return;
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
