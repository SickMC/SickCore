package me.anton.sickcore.modules.discord.modules.managers;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class SuggestionManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getGuild().equals(DiscordModule.getInstance().getMainGuild()))return;
        if (!event.getTextChannel().getId().equals(DiscordIds.suggestionchannel))return;
        if (event.getAuthor().isBot())return;

        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(event.getMember())
                .setTitle("SUGGESTION")
                .setContent("__Ersteller__ \n" + event.getMember().getAsMention() + "\n\n__Vorschlag__\n" + event.getMessage().getContentDisplay()).build();

        event.getTextChannel().sendMessageEmbeds(embed).queue();
        event.getTextChannel().deleteMessageById(event.getMessageId()).queue();
    }

}
