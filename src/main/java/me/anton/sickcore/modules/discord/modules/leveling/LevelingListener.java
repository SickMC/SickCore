package me.anton.sickcore.modules.discord.modules.leveling;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LevelingListener extends ListenerAdapter {

    private DatabaseModel model = DiscordModule.getInstance().getGamePlayer();
    private String[] allowed = {DiscordIds.chat, DiscordIds.suggestionchannel, DiscordIds.bugschannel, DiscordIds.supportchannel};
    private String[] allowedChannels = {DiscordIds.talk1, DiscordIds.talk2, DiscordIds.talk3};

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getGuild().equals(DiscordModule.getInstance().getMainGuild()))return;
        if (event.getAuthor().isBot())return;
        if (!Arrays.asList(allowed).contains(event.getTextChannel().getId()))return;
        if (!model.documentExists(Finder.stringFinder("userID", event.getAuthor().getId()))){
            if (DiscordAPIPlayerAdapter.isVerified(event.getAuthor().getId()))model.createDocument(new Document("userID", event.getAuthor().getId()).append("points", 1).append("enabled", true).append("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
            else model.createDocument(new Document("userID", event.getAuthor().getId()).append("points", 1).append("enabled", false).append("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
        }
        Document player = model.getDocument(Finder.stringFinder("userID", event.getAuthor().getId()));
        int oldPoint = player.getInteger("points");

        player.replace("points", oldPoint + 1);

        model.updateDocument(Finder.stringFinder("userID", event.getAuthor().getId()), player);

        for (DiscordLevel value : DiscordLevel.values()) {
            if (oldPoint == value.getEnd() && oldPoint + 1 == value.getNext().getStart()){
                if (value.getNext().getReward().expire() == -1){
                    event.getMessage().replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "** (permanent)").build()).queue();
                }else if (value.getNext().getReward().expire() == 0){
                    event.getMessage().replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "**").build()).queue();
                }else {
                    event.getMessage().replyEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "** (" + value.getNext().getReward().expire() + " days)").build()).queue();
                }
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if (!event.getGuild().equals(DiscordModule.getInstance().getMainGuild()))return;
        if (event.getMember().getUser().isBot())return;
        if (!Arrays.asList(allowedChannels).contains(event.getChannelJoined().getId()))return;

        if (!model.documentExists(Finder.stringFinder("userID", event.getMember().getUser().getId()))){
            if (DiscordAPIPlayerAdapter.isVerified(event.getMember().getUser().getId()))model.createDocument(new Document("userID", event.getMember().getUser().getId()).append("points", 5).append("enabled", true).append("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
            else model.createDocument(new Document("userID", event.getMember().getUser().getId()).append("points", 5).append("enabled", false).append("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)));
        }
        Document player = model.getDocument(Finder.stringFinder("userID", event.getMember().getUser().getId()));
        if (player.getLong("cooldown") > System.currentTimeMillis())return;

        int oldPoint = player.getInteger("points");

        player.replace("points", oldPoint + 5);
        player.replace("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));

        model.updateDocument(Finder.stringFinder("userID", event.getMember().getUser().getId()), player);

        for (DiscordLevel value : DiscordLevel.values()) {
            if (oldPoint == value.getEnd() && oldPoint + 1 == value.getNext().getStart()){
                TextChannel channel = DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.chat);
                if (value.getNext().getReward().expire() == -1){
                    channel.sendMessageEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "** (permanent)").build()).queue();
                }else if (value.getNext().getReward().expire() == 0){
                    channel.sendMessageEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "**").build()).queue();
                }else {
                    channel.sendMessageEmbeds(new EmbedBuilder(event.getMember()).setTitle("Level Up").mention(event.getMember()).setContent(event.getMember().getAsMention() + "\nYou gained a level up from **" + value.getName() + "** to **" + value.getNext().getName() + "**!"
                            + "\nYour reward is a " + value.getNext().getReward().getName() + ":\n >>> **" + value.getNext().getReward().getType() + ": " + value.getNext().getReward().getValue() + "** (" + value.getNext().getReward().expire() + " days)").build()).queue();
                }
            }
        }
    }
}
