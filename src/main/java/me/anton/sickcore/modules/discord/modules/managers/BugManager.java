package me.anton.sickcore.modules.discord.modules.managers;

import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BugManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getTextChannel().getId().equals(DiscordIds.bugschannel)){
            if (event.getMember().getUser().isBot())return;

            MessageEmbed build = new EmbedBuilder().setColor(Color.ORANGE)
                    .setFooter(DiscordMessages.getFooter(event.getAuthor()), DiscordMessages.getAvatarURL(event.getMember().getUser()))
                    .setTitle("**BUG | SickMC**")
                    .setDescription("__Ersteller__ \n" + event.getMember().getAsMention() + "\n\n__Bug__\n" + event.getMessage().getContentDisplay()).build();

            IDiscordPlayer player = new DiscordPlayer(event.getMember());

            event.getTextChannel().sendMessageEmbeds(build).queue();
            event.getTextChannel().deleteMessageById(event.getMessageId()).queue();
        }
    }
}
