package me.anton.sickcore.api.utils.minecraft.bukkit.player.sound;

import org.bukkit.Sound;

public class DefaultSounds {

    public static SoundBuilder pling = new SoundBuilder(Sound.BLOCK_NOTE_BLOCK_PLING, net.kyori.adventure.sound.Sound.Source.AMBIENT, 1,1);
    public static SoundBuilder levelUP = new SoundBuilder(Sound.ENTITY_PLAYER_LEVELUP, net.kyori.adventure.sound.Sound.Source.AMBIENT, 2,1);
    public static SoundBuilder anvil = new SoundBuilder(Sound.BLOCK_ANVIL_PLACE, net.kyori.adventure.sound.Sound.Source.AMBIENT, 1,1);

}
