package me.anton.sickcore.api.player.discordPlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import net.dv8tion.jda.api.entities.MessageEmbed;

public interface IDiscordPlayer {

    public MessageEmbed getEmbed(MessageEmbed en, MessageEmbed de);

    public String getMessage(String en, String de);

    public IAPIPlayer api();

}
