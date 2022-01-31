package me.anton.sickcore.modules.discord.modules.ticket;

import lombok.Getter;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;
import org.bson.Document;

public class Ticket {

    @Getter
    public boolean assigned;
    private Member assignedPlayer;
    private Member player;
    private Document ticket;
    @Getter
    private TextChannel ticketChannel;

    private TicketModule module = TicketModule.getInstance();
    private DiscordModule dModule = DiscordModule.getInstance();

    public Ticket(Member player){
        if (!module.getModel().documentExists("playerID", player.getUser().getId()))module.getModel().createDocument(new Document("playerID", player.getUser().getId())
                    .append("assignedID", "0")
                    .append("channelID", "0"));

        this.player = player;
        this.ticket = module.getModel().getDocument("playerID", player.getUser().getId());
        this.assigned = !ticket.getString("assignedID").equals("0");
        this.ticketChannel = dModule.getMainGuild().getTextChannelById(ticket.getString("channelID"));
    }

    public void open(){
        this.ticketChannel = dModule.getMainGuild().getCategoryById(module.getTicketCategoryID()).createTextChannel("ticket-" + player.getUser().getName()).complete();
        ticket.put("channelID", ticketChannel.getId());
        module.getModel().updateDocument("playerID",player.getUser().getId(), ticket);
        ticketChannel.putPermissionOverride(player).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.sendMessageEmbeds(new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(player).setTitle("Ticket").setContent(player.getAsMention() + "\nWillkommen im Support!\nEin " + dModule.getMainGuild().getRoleById(DiscordIds.mod).getAsMention() + " wird sich so schnell es geht um dich kümmern!\nWenn dein Anliegen geklärt ist kannst du das Ticket einfach\nmit dem :lock: Button ganz oben schließen!").build())
                        .setActionRow(Button.danger("ticket_close", Emoji.fromUnicode("\uD83D\uDD12")), Button.secondary("ticket_assign", Emoji.fromUnicode("\uD83D\uDCC3"))).queue();
    }

    public void assign(Member teamler){
        if (assigned)return;
        this.assignedPlayer = teamler;
        assigned = true;
        ticket.put("assignedID", teamler.getUser().getId());
        module.getModel().updateDocument("playerID", player.getUser().getId(), ticket);
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(assignedPlayer).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.sendMessageEmbeds(new EmbedBuilder(teamler).setTitle("Ticket").setContent("Das Ticket wurde von " + teamler.getAsMention() + " übernommen!").build()).queue();
    }

    public void close(IDiscordPlayer closer){
        ticketChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Ticket").setContent("Das Ticket wurde von " + closer.member().getAsMention() + " geschlossen!").build()).queue();
        ticketChannel.putPermissionOverride(player).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.getManager().setParent(dModule.getMainGuild().getCategoryById(module.getTicketArchiveCategoryID())).queue();
        module.getModel().deleteDocument("playerID",player.getUser().getId());
    }

}
