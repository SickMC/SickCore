package me.anton.sickcore.modules.rank.permission;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class BukkitPermissibleBase extends PermissibleBase {

    @Getter
    private APIPlayer player;

    public BukkitPermissibleBase(@Nullable Player player) {
        super(player);
        this.player = new APIPlayer(player.getUniqueId());
    }

    @Override
    public synchronized @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        Set<PermissionAttachmentInfo> infos = new HashSet<>();

        for (String permission : player.getRank().getPermissions()) {
            infos.add(new PermissionAttachmentInfo(this, permission, null, true));
        }

        return infos;
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return this.hasPermission(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return this.hasPermission(perm.getName());
    }

    @Override
    public boolean hasPermission(@NotNull String inName) {
        return player.getRank().getPermissions().contains(inName) || player.isAdmin();
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return hasPermission(perm.getName());
    }



}
