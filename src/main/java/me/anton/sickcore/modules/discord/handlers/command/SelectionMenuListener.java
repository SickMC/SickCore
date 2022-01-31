package me.anton.sickcore.modules.discord.handlers.command;

import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SelectionMenuListener extends ListenerAdapter {

    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        SelectionMenuHelper helper = SelectionMenuHelper.get(event.getSelectionMenu());
        if(helper == null) return;
        helper.onResult(event);
    }
}
