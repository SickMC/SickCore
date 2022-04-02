package me.anton.sickcore.modules.rank.permission;

import me.anton.sickcore.api.handler.listeners.bungee.BungeeHandler;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.modules.rank.Rank;
import net.md_5.bungee.api.event.PermissionCheckEvent;

public class PermissionCheck extends BungeeHandler{

    @Override
    public void onPermissionCheck(PermissionCheckEvent rawEvent, String permission, BungeePlayer player) {
        if (player.api().isAdmin() || player.api().getRank().equals(new Rank("NickedAdmin")) || player.api().getRank().getPermissions().contains(permission)){
            rawEvent.setHasPermission(true);
            return;
        }
        rawEvent.setHasPermission(false);
    }
}
