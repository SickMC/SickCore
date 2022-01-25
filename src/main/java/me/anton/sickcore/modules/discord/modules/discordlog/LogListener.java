package me.anton.sickcore.modules.discord.modules.discordlog;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
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

import java.util.stream.Collectors;

public class LogListener extends ListenerAdapter {

    private Guild guild = DiscordModule.getInstance().getMainGuild();
    DiscordLogModule module = DiscordLogModule.getInstance();

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Join")
                .setContent(event.getMember().getAsMention() + " joined the server!").build();
        module.log(builder);
    }

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent event) {
        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("InviteCreate")
                .setContent(event.getInvite().getInviter().getAsMention() + " created the invite: " + event.getInvite().getUrl()).build();
        module.log(builder);
    }

    @Override
    public void onGuildInviteDelete(@NotNull GuildInviteDeleteEvent event) {
        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("InviteDelete")
                .setContent("The invite " + event.getUrl() + " was deleted!").build();
        module.log(builder);
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("RoleAdd")
                .setContent(event.getMember().getAsMention() + " got the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("RoleRemove")
                .setContent(event.getMember().getAsMention() + " lost the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberUpdateAvatar(@NotNull GuildMemberUpdateAvatarEvent event) {
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("AvatarUpdate")
                .setContent(event.getMember().getAsMention() + "'s avatar changed from: \n" + event.getOldAvatarUrl() + " to: " + event.getNewAvatarUrl()).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("NicknameChange")
                .setContent(event.getMember().getAsMention() + "'s nickname changed from: \n__" + event.getOldNickname() + "__ to: " + event.getNewNickname() + "__").build();
        module.log(embed);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        guild.retrieveAuditLogs()
                .type(ActionType.MEMBER_VOICE_KICK)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Voice Kick")
                            .setContent(event.getMember().getAsMention() + " was kicked by: " + entry.getUser().getAsMention() + "\n from channel: " + event.getOldValue().getAsMention()).build();
                    module.log(embed);
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
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Voice Move")
                            .setContent(event.getMember().getAsMention() + " was moved by: " + entry.getUser().getAsMention() + "\n from " + event.getOldValue().getAsMention() + " to " + event.getNewValue().getAsMention()).build();

                    module.log(embed);
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
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Ban")
                            .setContent(event.getUser().getAsTag() + " was banned by: " + entry.getUser().getAsMention() + " with the reason: " + entry.getReason()).build();

                    module.log(embed);
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
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Unban")
                            .setContent(event.getUser().getAsTag() + " was unbanned by: " + entry.getUser().getAsMention()).build();

                    module.log(embed);
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
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Kick")
                            .setContent(event.getUser().getAsTag() + " was kicked by: " + entry.getUser().getAsMention() + " with the reason: "+ entry.getReason()).build();

                    module.log(embed);
                });
    }

}
