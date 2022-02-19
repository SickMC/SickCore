package me.anton.sickcore.modules.discord.modules.ticket;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import me.anton.sickcore.modules.discord.modules.discordlog.DiscordLogModule;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class TicketListener extends ListenerAdapter {

    private final TicketModule ticketModule = TicketModule.getInstance();

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (!event.getGuild().equals(DiscordModule.getInstance().getMainGuild()))return;
        switch (event.getComponentId()) {
            case "ticket_create" -> {
                if (ticketModule.getModel().documentExists(Finder.stringFinder("playerID", event.getMember().getUser().getId()))) {
                    event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Du hast bereits ein Ticket geöffnet!").build()).setEphemeral(true).queue();
                    DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent(event.getMember().getAsMention() + " hat versucht ein Ticket zu öffnen.\nEr hatte aber schon eins geöffnet!").build());
                    return;
                }
                Ticket ticket = new Ticket(event.getMember());
                ticket.open();
                DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Der Spieler " + event.getMember().getAsMention() + " hat das Ticket " + ticket.getTicketChannel().getAsMention() + " erstellt!").build());
                event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Your ticket is opened in " + ticket.getTicketChannel().getAsMention()).build()).setEphemeral(true).queue();
                break;
            }
            case "ticket_assign" -> {
                Ticket ticket = TicketAdapter.getTicketByChannel(event.getTextChannel());
                if (ticket.isAssigned()) {
                    event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Das Ticket wurde bereits übernommen!").build()).setEphemeral(true).queue();
                    DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent(event.getMember().getAsMention() + " hat versucht das Ticket " + event.getTextChannel().getAsMention() + ".\nEs wurde aber bereits übernommen!").build());
                    return;
                }

                ticket.assign(event.getMember());
                DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Das Ticket " + event.getTextChannel().getAsMention() + " wurde von " + event.getMember().getAsMention() + " übernommen!").build());
                event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Du hast das Ticket übernommen!").build()).setEphemeral(true).queue();
                break;
            }
            case "ticket_close" -> {
                Ticket ticket = TicketAdapter.getTicketByChannel(event.getTextChannel());
                ticket.close(new DiscordPlayer(event.getMember()));
                DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Das Ticket " + event.getTextChannel().getAsMention() + " wurde von " + event.getMember().getAsMention() + " geschlossen!").build());
                event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Du hast das Ticket geschlossen!").build()).setEphemeral(true).queue();
                break;
            }
            case "ticket_delete" -> {
                event.getTextChannel().delete().queueAfter(5, TimeUnit.SECONDS);
                DiscordLogModule.getInstance().log(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Die Ticket History von " + event.getTextChannel().getAsMention() + " wurde gelöscht!").build());
                event.replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Ticket").setContent("Das Ticket löscht sich in einigen Sekunden!").build()).queue();
                break;
            }
        }
    }
}
