package me.anton.sickcore.modules.discord.modules.managers;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BugManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getTextChannel().getId().equals(DiscordIds.bugschannel))return;
        if (event.getMember().getUser().isBot())return;

        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(event.getMember())
                .setTitle("BUG")
                .setContent("__Ersteller__ \n" + event.getMember().getAsMention() + "\n\n__Bug__\n" + event.getMessage().getContentDisplay()).build();

        event.getTextChannel().sendMessageEmbeds(embed).queue();
        event.getTextChannel().deleteMessageById(event.getMessageId()).queue();
    }
}
