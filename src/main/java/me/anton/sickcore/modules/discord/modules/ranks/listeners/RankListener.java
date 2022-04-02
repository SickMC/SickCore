package me.anton.sickcore.modules.discord.modules.ranks.listeners;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.modules.discord.modules.ranks.RankUpdate;
import net.md_5.bungee.api.event.PostLoginEvent;

public class RankListener extends BungeeHandler {

    @Override
    public void onPostJoin(PostLoginEvent rawEvent, BungeePlayer player) {
        new RankUpdate(player);
    }

}
