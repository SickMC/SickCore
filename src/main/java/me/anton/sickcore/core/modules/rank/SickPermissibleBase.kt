package me.anton.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.player.SickPlayers
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissibleBase
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachmentInfo

class SickPermissibleBase(private val player: Player) : PermissibleBase(player) {

    val permissions = ArrayList<String>()

    override fun isPermissionSet(name: String): Boolean {
        return permissions.contains(name)
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        return permissions.contains(perm.name)
    }

    override fun hasPermission(inName: String): Boolean {
        return permissions.contains(inName)
    }

    override fun hasPermission(perm: Permission): Boolean {
        return permissions.contains(perm.name)
    }

    override fun recalculatePermissions() {
        Core.instance.databaseScope.launch {
            val list = SickPlayers.getSickPlayer(player.uniqueId)!!.getPermissions()
        }
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        val infos = HashSet<PermissionAttachmentInfo>()

        permissions.forEach {
            infos.add(PermissionAttachmentInfo(this, it, null, true))
        }

        return infos
    }
}