package me.anton.sickcore.modules.discord.modules.lobby;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class LobbyLog extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(event.getMember())
                .setTitle("Join")
                .setContent(event.getMember().getAsMention() + " joined the server! :partying_face:").build();
        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.lobbyChannel).sendMessageEmbeds(embed).queue();
    }
}
