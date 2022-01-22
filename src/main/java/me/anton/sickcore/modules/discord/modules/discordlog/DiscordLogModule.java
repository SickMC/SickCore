package me.anton.sickcore.modules.discord.modules.discordlog;

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

    public void loadEmbed(){
        TextChannel textChannel = DiscordModule.getInstance().getJda().getTextChannelById(DiscordIds.discordLogChannel);
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**Load | SickMC**")
                .setDescription("Proxy loaded!")
                .setFooter(DiscordMessages.getFooter(DiscordModule.getInstance().getJda().getSelfUser()), DiscordMessages.getAvatarURL(DiscordModule.getInstance().getJda().getSelfUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }

    public void unloadEmbed(){
        TextChannel textChannel = DiscordModule.getInstance().getJda().getTextChannelById(DiscordIds.discordLogChannel);
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**Unload | SickMC**")
                .setDescription("Proxy unloaded!")
                .setFooter(DiscordMessages.getFooter(DiscordModule.getInstance().getJda().getSelfUser()), DiscordMessages.getAvatarURL(DiscordModule.getInstance().getJda().getSelfUser()))
                .setColor(Color.ORANGE).build();
        textChannel.sendMessageEmbeds(embed).queue();
    }
}
