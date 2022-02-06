package me.anton.sickcore.games.defaults.all.appereance;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.event.command.UnknownCommandEvent;

public class UnknownCommandProvider extends BukkitHandler {

    @Override
    public void onPlayerUnknownCommand(UnknownCommandEvent rawEvent, String command) {
        rawEvent.message(Component.text("§7The command §6/" + command + "§7 cannot be found!"));
    }
}
