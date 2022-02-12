package me.anton.sickcore.games.all.nick;

import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.core.BukkitCore;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.haoshoku.nick.events.NickFinishEvent;

public class NickProvider extends BukkitHandler {

    @Override
    public void onPlayerNickFinish(NickFinishEvent rawEvent, IBukkitPlayer bukkitPlayer, String nickname) {
        if (!BukkitCore.getInstance().getCurrentGame().nick()) {
            if (bukkitPlayer.isNicked())bukkitPlayer.unnick();
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, IBukkitPlayer bukkitPlayer) {
        if (!BukkitCore.getInstance().getCurrentGame().nick())return;
        if (!bukkitPlayer.api().hasAutoNick())return;
        bukkitPlayer.nick();
    }
}
