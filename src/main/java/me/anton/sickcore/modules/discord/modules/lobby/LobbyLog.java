package me.anton.sickcore.modules.discord.modules.lobby;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class LobbyLog extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("**Join|SickMC**")
                .setDescription(event.getMember().getAsMention() + " joined the server! :partying_face:")
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.lobbyChannel).sendMessageEmbeds(embed).queue();
    }
}
