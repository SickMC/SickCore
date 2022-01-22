package me.anton.sickcore.modules.discord.modules.discordlog;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.stream.Collectors;

public class LogListener extends ListenerAdapter {

    Guild guild = DiscordModule.getInstance().getMainGuild();
    TextChannel textChannel = guild.getTextChannelById(DiscordIds.discordLogChannel);

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**Join | SickMC**")
                .setDescription(event.getMember().getAsMention() + " joined the server!")
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**InviteCreate | SickMC**")
                .setDescription(event.getInvite().getInviter().getAsMention() + " created the invite: " + event.getInvite().getUrl())
                .setFooter(DiscordMessages.getFooter(event.getInvite().getInviter()), DiscordMessages.getAvatarURL(event.getInvite().getInviter()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildInviteDelete(@NotNull GuildInviteDeleteEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**InviteDelete | SickMC**")
                .setDescription("The invite " + event.getUrl() + " was deleted!")
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**RoleAdd | SickMC**")
                .setDescription(event.getMember().getAsMention() + " got the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", ")))
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**RoleRemove | SickMC**")
                .setDescription(event.getMember().getAsMention() + " lost the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", ")))
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildMemberUpdateAvatar(@NotNull GuildMemberUpdateAvatarEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**AvatarUpdate | SickMC**")
                .setDescription(event.getMember().getAsMention() + "'s nickname changed from: \n__" + event.getOldAvatarUrl() + "__ to: __" + event.getNewAvatarUrl() + "__")
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**NicknameChange | SickMC**")
                .setDescription(event.getMember().getAsMention() + "'s nickname changed from: \n__" + event.getOldNickname() + "__ to: " + event.getNewNickname() + "__")
                .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.MEMBER_VOICE_KICK)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setTitle("**Voice Kick | SickMC**")
                            .setDescription(event.getMember().getAsMention() + " was kicked by: " + entry.getUser().getAsMention() + "\n from channel: " + event.getOldValue().getAsMention())
                            .setFooter(DiscordMessages.getFooter(event.getMember().getUser()), DiscordMessages.getAvatarURL(event.getMember().getUser()))
                            .setColor(Color.ORANGE).build();
                    textChannel.sendMessageEmbeds(embed).queue();
                });
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.MEMBER_VOICE_MOVE)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setTitle("**Voice Move | SickMC**")
                            .setDescription(event.getMember().getAsMention() + " was moved by: " + entry.getUser().getAsMention() + "\n from " + event.getOldValue().getAsMention() + " to " + event.getNewValue().getAsMention())
                            .setFooter(DiscordMessages.getFooter(event.getMember().getUser()), DiscordMessages.getAvatarURL(event.getMember().getUser()))
                            .setColor(Color.ORANGE).build();
                    textChannel.sendMessageEmbeds(embed).queue();
                });
    }

    @Override
    public void onGuildBan(@NotNull GuildBanEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.BAN)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setTitle("**Ban | SickMC**")
                            .setDescription(event.getUser().getAsTag() + " was banned by: " + entry.getUser().getAsMention() + " with the reason: " + entry.getReason())
                            .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                            .setColor(Color.ORANGE).build();
                    textChannel.sendMessageEmbeds(embed).queue();
                });
    }

    @Override
    public void onGuildUnban(@NotNull GuildUnbanEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.UNBAN)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setTitle("**Unban | SickMC**")
                            .setDescription(event.getUser().getAsTag() + " was unbanned by: " + entry.getUser().getAsMention())
                            .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                            .setColor(Color.ORANGE).build();
                    textChannel.sendMessageEmbeds(embed).queue();
                });
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.KICK)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new EmbedBuilder()
                            .setTimestamp(Instant.now())
                            .setTitle("**Kick | SickMC**")
                            .setDescription(event.getUser().getAsTag() + " was kicked by: " + entry.getUser().getAsMention() + " with the reason: "+ entry.getReason())
                            .setFooter(DiscordMessages.getFooter(event.getUser()), DiscordMessages.getAvatarURL(event.getUser()))
                            .setColor(Color.ORANGE).build();
                    textChannel.sendMessageEmbeds(embed).queue();
                });
    }

}
