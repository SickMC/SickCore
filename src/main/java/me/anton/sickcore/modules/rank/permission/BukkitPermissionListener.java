package me.anton.sickcore.modules.rank.permission;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerLoginEvent;

public class BukkitPermissionListener extends BukkitHandler {

    @Override
    public void onPlayerLogin(PlayerLoginEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (rawEvent.getResult() != PlayerLoginEvent.Result.ALLOWED)return;

        try {
            BukkitPermissionInjectionHelper.injectPlayer(rawEvent.getPlayer());
        } catch (Throwable throwable) {
            Logger.error("Permissible base loading of player" + rawEvent.getPlayer().getName() + " failed!" + "\n" + throwable, this.getClass());
            rawEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§4Internal Error: Please report this bug in a ticket\n §7Discord: dc.sickmc.net"));
        }
    }
}
