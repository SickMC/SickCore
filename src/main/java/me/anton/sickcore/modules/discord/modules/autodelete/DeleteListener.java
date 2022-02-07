package me.anton.sickcore.modules.discord.modules.autodelete;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import me.anton.sickcore.modules.discord.modules.discordlog.DiscordLogModule;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DeleteListener extends ListenerAdapter {

    public static List<String> deletions = new ArrayList<>();

    public DeleteListener(){
        deletions.addAll(AutoDeleteModule.getInstance().getDocument().getList("lightDeletions", String.class));
        deletions.addAll(AutoDeleteModule.getInstance().getDocument().getList("hardDeletions", String.class));
        deletions.addAll(AutoDeleteModule.getInstance().getDocument().getList("links", String.class));
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMember().getRoles().contains(DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.mod))
                || event.getMember().getRoles().contains(DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.staff))
                || event.getMember().getRoles().contains(DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.admin))
                || event.getMember().getUser().equals(DiscordModule.getInstance().getJda().getSelfUser()))return;
        for (String s : event.getMessage().getContentRaw().split(" ")){
            if (!deletions.contains(s))return;
            MessageEmbed reply = new EmbedBuilder(event.getMember())
                    .setTitle("Chat Filter")
                    .setContent(event.getMember().getAsMention() + "\nYour message was deleted!\n\n__Please watch your choices of words!__\n\n**Reason:** \n```" + s + "```")
                    .build();
            Message message = event.getMessage().replyEmbeds(reply).complete();
            message.delete().queueAfter(5, TimeUnit.SECONDS);

            MessageEmbed embed = new EmbedBuilder(event.getMember())
                    .setTitle("Auto Delete")
                    .setContent(event.getMember().getAsMention() + "'s message was deleted!\n\nKeyword: **" + s + "** \n\nContent: ```" + event.getMessage().getContentRaw() + "```")
                    .build();
            DiscordLogModule.getInstance().log(embed);

            event.getMessage().delete().queue();
        }
    }
}
