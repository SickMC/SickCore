package me.anton.sickcore.api.player.discordPlayer;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public interface IDiscordPlayer {

    public MessageEmbed getEmbed(MessageEmbed en, MessageEmbed de);

    public String getMessage(String en, String de);

    public IAPIPlayer api();

    public User user();

    public Member member();

}
