package me.anton.sickcore.api.utils.minecraft.bukkit.player.sound;

import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundBuilder {

    private Player player;
    private Sound sound;
    private net.kyori.adventure.sound.Sound.Source source;
    private int pitch;
    private int volume;

    public SoundBuilder(Sound sound, net.kyori.adventure.sound.Sound.Source source, int pitch, int volume){
        this.sound = sound;
        this.source = source;
        this.pitch = pitch;
        this.volume = volume;
    }

    public void play(Player player){
        this.player = player;
        player.playSound(net.kyori.adventure.sound.Sound.sound(sound.getKey(), source, pitch, volume));
    }

    public void play(IBukkitPlayer player){
        player.getPlayer().playSound(net.kyori.adventure.sound.Sound.sound(sound.getKey(), source, pitch, volume));
    }

}
