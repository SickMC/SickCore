package me.anton.sickcore.modules.discord.modules.verify;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyCleaner extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getGuild().equals(DiscordModule.getInstance().getMainGuild()))return;
        if (!event.getTextChannel().getId().equals(DiscordIds.verifychannel))return;
        if (event.getMember().getUser().isBot()){event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);return;}
        event.getMessage().delete().queue();
    }
}
