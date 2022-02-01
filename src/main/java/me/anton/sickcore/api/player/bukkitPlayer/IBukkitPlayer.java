package me.anton.sickcore.api.player.bukkitPlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.messages.CommandMessages;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface IBukkitPlayer {

    public void sendMessage(String en, String de);

    public void sendMessage(Component en, Component de);

    public void nick();

    public void unnick();

    public boolean isNicked();

    public String getName();

    public String getNickSkinName();

    public String getNickedPrefix();

    public IAPIPlayer api();

    public CommandMessages message();

    public Player getPlayer();

    public void sendTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder);

    public void sendAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilde);

    public void sendCustomAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder, int speed, SoundBuilder finishSound);

    public void stopAllTitles();

    public void playSound(Sound sound, int pitch, int volume);

    public void playSound(SoundBuilder sound);

    public void vanish();

    public void unVanish();

}
