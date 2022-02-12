package me.anton.sickcore.api.player.bukkitPlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface IBukkitPlayer {

    public void nick();

    public void unnick();

    public boolean isNicked();

    public void sendMessage(LanguagePath path);

    public void sendMessage(LanguageObject object);

    public String getName();

    public String getNickSkinName();

    public String getNickDisplayName();

    public IAPIPlayer api();

    public Player getPlayer();

    public void sendTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder);

    public void sendAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilde);

    public void sendCustomAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder, int speed, SoundBuilder finishSound);

    public void stopAllTitles();

    public void playSound(Sound sound, int pitch, int volume);

    public void playSound(SoundBuilder sound);

    public void vanish();

    public void unVanish();

    public void kick(String reasonen, String reasonde);

}
