package me.anton.sickcore.modules.discord.modules.verify;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyCleaner extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getTextChannel().getId().equals(DiscordIds.verifychannel))return;
        event.getMessage().delete().queueAfter(10, TimeUnit.SECONDS);
    }
}
