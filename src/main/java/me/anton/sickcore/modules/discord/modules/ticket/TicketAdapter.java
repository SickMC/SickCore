package me.anton.sickcore.modules.discord.modules.ticket;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import net.dv8tion.jda.api.entities.TextChannel;

public class TicketAdapter {

    public static Ticket getTicketByChannel(TextChannel channel){
        String id = (String) TicketModule.getInstance().getModel().get("playerID", new Finder("channelID", channel.getId()));
        IDiscordPlayer player = new DiscordPlayer(channel.getGuild().getMemberById(id));

        return new Ticket(player);
    }

}
