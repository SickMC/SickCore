package me.anton.sickcore.modules.discord.modules.ticket;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bson.Document;

public class TicketAdapter {

    public static Ticket getTicketByChannel(TextChannel channel){
        Document document = TicketModule.getInstance().getModel().getDocument(new Finder("channelID", channel.getId()));
        Member member = DiscordModule.getInstance().getMainGuild().getMemberById((String) document.get("playerID"));
        return new Ticket(member);
    }

}
