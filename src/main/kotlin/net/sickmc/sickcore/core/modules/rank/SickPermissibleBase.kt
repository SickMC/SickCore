package net.sickmc.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.player.SickPlayer
import net.sickmc.sickcore.core.player.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissibleBase
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachmentInfo

class SickPermissibleBase(private val player: Player) : PermissibleBase(player) {

    private var permissions = arrayListOf<String>()
    private var operator = validateAdmin()
    private var sickPlayer = SickPlayers.getCachedSickPlayer(player.uniqueId)

    override fun isPermissionSet(name: String): Boolean{
        return operator || permissions.contains(name)
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        return operator || permissions.contains(perm.name)
    }

    override fun hasPermission(inName: String): Boolean {
        return operator || permissions.contains(inName)
    }

    override fun hasPermission(perm: Permission): Boolean {
        return operator || permissions.contains(perm.name)
    }

    override fun recalculatePermissions() {
        databaseScope.launch {sickPlayer = SickPlayers.reloadPlayer(player.uniqueId) ?: error("Failed to reload SickPlayer") }
        permissions = arrayListOf()
        permissions.addAll(sickPlayer.permissions)
        operator = validateAdmin()
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        val infos = HashSet<PermissionAttachmentInfo>()

        permissions.forEach {
            infos.add(PermissionAttachmentInfo(this, it, null, true))
        }

        return infos
    }

    private fun validateAdmin(): Boolean{
        return sickPlayer.rank.parent.name == "Administration"
    }
    override fun isOp(): Boolean {
        return operator
    }
}