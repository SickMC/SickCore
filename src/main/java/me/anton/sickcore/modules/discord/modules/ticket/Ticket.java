package me.anton.sickcore.modules.discord.modules.ticket;

import lombok.Getter;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.Button;
import org.bson.Document;

public class Ticket {

    @Getter
    public boolean assigned;
    private IDiscordPlayer assignedPlayer;
    private IDiscordPlayer player;
    private Document ticket;
    @Getter
    private TextChannel ticketChannel;

    private TicketModule module = TicketModule.getInstance();
    private DiscordModule dModule = DiscordModule.getInstance();

    public Ticket(IDiscordPlayer player){
        if (!module.getModel().documentExists("playerID", player.user().getId()))module.getModel().createDocument(new Document("playerID", player.user().getId())
                    .append("assignedID", "0")
                    .append("channelID", "0"));

        this.ticket = module.getModel().getDocument("playerID", player.user().getId());
        this.player = player;
        this.assigned = !ticket.getString("assignedID").equals("0");
    }

    public void open(){
        this.ticketChannel = dModule.getMainGuild().getCategoryById(module.getTicketCategoryID()).createTextChannel("ticket-" + player.member().getEffectiveName()).complete();
        ticket.put("channelID", ticketChannel.getId());
        module.getModel().updateDocument("playerID", player.user().getId(), ticket);
        ticketChannel.putPermissionOverride(player.member()).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.sendMessageEmbeds(new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(player.member()).setTitle("Ticket").setContent(player.member().getAsMention() + "\nWillkommen im Support!\nEin " + dModule.getMainGuild().getRoleById(DiscordIds.mod).getAsMention() + " wird sich so schnell es geht um dich kümmern!\nWenn dein Anliegen geklärt ist kannst du das Ticket einfach\nmit dem :lock: Button ganz oben schließen!").build())
                        .setActionRow(Button.danger("ticket_close", Emoji.fromUnicode("\uD83D\uDD12")), Button.secondary("ticket_assign", Emoji.fromUnicode("\uD83D\uDCC3"))).queue();
    }

    public void assign(IDiscordPlayer teamler){
        if (assigned)return;
        this.assignedPlayer = teamler;
        assigned = true;
        ticket.put("channelID", teamler.user().getId());
        module.getModel().updateDocument("playerID", player.user().getId(), ticket);
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(assignedPlayer.member()).setAllow(Permission.VIEW_CHANNEL).queue();
        ticketChannel.sendMessageEmbeds(new EmbedBuilder(teamler.member()).setTitle("Ticket").setContent("Das Ticket wurde von " + teamler.member().getAsMention() + " übernommen!").build()).queue();
    }

    public void close(IDiscordPlayer closer){
        ticketChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("Ticket").setContent("Das Ticket wurde von " + closer.member().getAsMention() + " geschlossen!").build()).queue();
        ticketChannel.putPermissionOverride(player.member()).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.putPermissionOverride(dModule.getJda().getRoleById(DiscordIds.mod)).setDeny(Permission.VIEW_CHANNEL).queue();
        ticketChannel.getManager().setParent(dModule.getMainGuild().getCategoryById(module.getTicketArchiveCategoryID())).queue();
        module.getModule().getModel().deleteDocument("playerID", player.user().getId());
    }

}
