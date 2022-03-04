package me.anton.sickcore.modules.discord.modules.ranks.listeners;

import eu.thesimplecloud.api.eventapi.CloudEventHandler;
import eu.thesimplecloud.api.eventapi.IListener;
import eu.thesimplecloud.module.permission.event.player.PermissionPlayerUpdatedEvent;
import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.modules.discord.modules.ranks.RankUpdate;
import net.md_5.bungee.api.event.PostLoginEvent;

public class RankListener extends BungeeHandler implements IListener {

    @Override
    public void onPostJoin(PostLoginEvent rawEvent, BungeePlayer player) {
        new RankUpdate(player);
    }

    @CloudEventHandler
    public void onPerm(PermissionPlayerUpdatedEvent event){
        CloudAPIPlayer player = new CloudAPIPlayer(event.getPlayer().getUniqueId());
        new RankUpdate(player.api().bungee());
    }
}
