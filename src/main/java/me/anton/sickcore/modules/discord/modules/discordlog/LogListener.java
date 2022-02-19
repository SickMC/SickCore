package me.anton.sickcore.modules.discord.modules.discordlog;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.autodelete.DeleteListener;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.*;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateAvatarEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LogListener extends ListenerAdapter {

    private Guild guild = DiscordModule.getInstance().getMainGuild();
    DiscordLogModule module = DiscordLogModule.getInstance();

    public final Map<Long, Message> cache = new ConcurrentHashMap<>();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(!event.getGuild().equals(guild))return;
        cache.put(event.getMessageIdLong(), event.getMessage());
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if(!event.getGuild().equals(guild))return;
        if (event.getUser().isBot())return;
        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Join")
                .setContent(event.getMember().getAsMention() + " joined the server!").build();
        module.log(builder);
    }

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent event) {
        if(!event.getGuild().equals(guild))return;

        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("InviteCreate")
                .setContent(event.getInvite().getInviter().getAsMention() + " created the invite: " + event.getInvite().getUrl()).build();
        module.log(builder);
    }

    @Override
    public void onGuildInviteDelete(@NotNull GuildInviteDeleteEvent event) {
        if(!event.getGuild().equals(guild))return;

        MessageEmbed builder = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("InviteDelete")
                .setContent("The invite " + event.getUrl() + " was deleted!").build();
        module.log(builder);
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        if(!event.getGuild().equals(guild))return;
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("RoleAdd")
                .setContent(event.getMember().getAsMention() + " got the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        if(!event.getGuild().equals(guild))return;
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("RoleRemove")
                .setContent(event.getMember().getAsMention() + " lost the role " + event.getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))  + "!" +
                        "\nHe/She has now the roles: " + event.getMember().getRoles().parallelStream().map(role -> role.getAsMention()).collect(Collectors.joining(", "))).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberUpdateAvatar(@NotNull GuildMemberUpdateAvatarEvent event) {
        if(!event.getGuild().equals(guild))return;
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("AvatarUpdate")
                .setContent(event.getMember().getAsMention() + "'s avatar changed from: \n" + event.getOldAvatarUrl() + " to: " + event.getNewAvatarUrl()).build();
        module.log(embed);
    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        if(!event.getGuild().equals(guild))return;
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("NicknameChange")
                .setContent(event.getMember().getAsMention() + "'s nickname changed from: \n__" + event.getOldNickname() + "__ to: __" + event.getNewNickname() + "__").build();
        module.log(embed);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if(!event.getGuild().equals(guild))return;
        if (event.getEntity().getUser().isBot())return;
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
        if(!event.getGuild().equals(guild))return;
        if (event.getEntity().getUser().isBot())return;
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
        if(!event.getGuild().equals(guild))return;
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
        if(!event.getGuild().equals(guild))return;
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
        if(!event.getGuild().equals(guild))return;
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

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if(!event.getGuild().equals(guild))return;
        if (!cache.containsKey(event.getMessageIdLong()))return;
        for (String s : cache.get(event.getMessageIdLong()).getContentRaw().split(" "))
            if (DeleteListener.deletions.contains(s))return;

        guild.retrieveAuditLogs()
                .type(ActionType.MESSAGE_DELETE)
                .limit(1)
                .queue(list ->{
                    if (list.isEmpty())return;
                    AuditLogEntry entry = list.get(0);
                    if (entry.getUser().isBot())return;
                    if (cache.get(event.getMessageIdLong()).getAuthor().isBot())return;
                    MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                            .setTitle("Message Delete")
                            .setContent("A message of " + cache.get(event.getMessageIdLong()).getAuthor().getAsMention() + " was deleted by: " + entry.getUser().getAsMention() + "\n\n**Content:** \n```" + cache.get(event.getMessageIdLong()).getContentRaw() + "```").build();

                    module.log(embed);
                });
    }

    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        if(!event.getGuild().equals(guild))return;
        if (!cache.containsKey(event.getMessageIdLong()))return;
        if (event.getMember().getUser().isBot())return;
        if (cache.get(event.getMessageIdLong()).getAuthor().isBot())return;
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Message Update")
                .setContent("A message of " + event.getAuthor().getAsMention() + " was edited!\n\n**Old Content:** \n```" + cache.get(event.getMessageIdLong()).getContentRaw() + "```\n **New Content:** \n```" + event.getMessage().getContentRaw() + "```").build();

        module.log(embed);
    }

}
