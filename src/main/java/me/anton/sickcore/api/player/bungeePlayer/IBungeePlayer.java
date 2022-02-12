package me.anton.sickcore.api.player.bungeePlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public interface IBungeePlayer {

    public void sendMessage(LanguageObject object);

    public void sendMessage(LanguagePath path);

    public IAPIPlayer api();

    public ProxiedPlayer getPlayer();
}
