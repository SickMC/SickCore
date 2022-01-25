package me.anton.sickcore.modules.discord.modules.discordlog;

import lombok.Getter;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.modules.IDiscordModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.Instant;

public class DiscordLogModule implements IDiscordModule {

    @Getter
    private static DiscordLogModule instance;

    @Override
    public void load() {
        register();
    }

    @Override
    public void unload() {
        unloadEmbed();
    }

    @Override
    public void register() {
        loadEmbed();

        DiscordModule.getInstance().getJda().addEventListener(new LogListener());
    }

    public void log(MessageEmbed embed){
        TextChannel textChannel = DiscordModule.getInstance().getJda().getTextChannelById(DiscordIds.discordLogChannel);
        textChannel.sendMessageEmbeds(embed).queue();
    }

    public void loadEmbed(){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Loas")
                .setContent("Proxy loaded!");
        log(embed.build());
    }

    public void unloadEmbed(){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Unload")
                .setContent("Proxy unloaded!");
        log(embed.build());
    }
}
