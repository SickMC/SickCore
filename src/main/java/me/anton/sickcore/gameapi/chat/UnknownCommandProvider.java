package me.anton.sickcore.gameapi.chat;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.command.UnknownCommandEvent;

public class UnknownCommandProvider extends BukkitHandler {

    @Override
    public void onPlayerUnknownCommand(UnknownCommandEvent rawEvent, String command, BukkitPlayer player) {
        rawEvent.message(Component.text("§7The command §6/" + command + "§7 cannot be found!"));
    }

}
