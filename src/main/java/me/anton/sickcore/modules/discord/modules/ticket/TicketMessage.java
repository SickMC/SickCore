package me.anton.sickcore.modules.discord.modules.ticket;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;

public class TicketMessage {

    private DiscordModule module = DiscordModule.getInstance();

    public TicketMessage(){
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Ticket")
                .setContent("Wenn du Hilfe brauchst kannst du einfach ein Ticket erstellen\nindem du unten auf den :envelope_with_arrow: Button klickst!")
                .withoutTimeStamp().build();

        module.getMainGuild().getTextChannelById(TicketModule.getInstance().getTicketChannel()).getHistoryFromBeginning(1).queue(history -> {
            if (history.isEmpty())
                module.getMainGuild().getTextChannelById(TicketModule.getInstance().getTicketChannel()).sendMessageEmbeds(embed).setActionRow(Button.of(ButtonStyle.PRIMARY, "ticket_create", Emoji.fromUnicode("\uD83D\uDCE9"))).queue();
        });
    }

}
