package net.sickmc.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.sickmc.sickcore.core.player.SickPlayer
import net.sickmc.sickcore.core.player.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissibleBase
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachmentInfo

class SickPermissibleBase(val player: Player) : PermissibleBase(player) {

    private val permissions = ArrayList<String>()
    private val sickPlayer = validatePlayer()

    override fun isPermissionSet(name: String): Boolean{
        var bool = false
        databaseScope.launch {
            bool = sickPlayer.getRank().getParent().name == "Administration" || permissions.contains(name)
        }
        return bool
    }

    override fun isPermissionSet(perm: Permission): Boolean {
        var bool = false
        databaseScope.launch {
            bool = sickPlayer.getRank().getParent().name == "Administration" || permissions.contains(perm.name)
        }
        return bool
    }

    override fun hasPermission(inName: String): Boolean {
        var bool = false
        databaseScope.launch {
            bool = sickPlayer.getRank().getParent().name == "Administration" || permissions.contains(inName)
        }
        return bool
    }

    override fun hasPermission(perm: Permission): Boolean {
        var bool = false
        databaseScope.launch {
            bool = sickPlayer.getRank().getParent().name == "Administration" || permissions.contains(perm.name)
        }
        return bool
    }

    override fun recalculatePermissions() {
        runBlocking {
            val list = SickPlayers.getSickPlayer(player.uniqueId)?.getPermissions()
            list?.forEach {
                permissions.add(it)
            }
        }
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        val infos = HashSet<PermissionAttachmentInfo>()

        permissions.forEach {
            infos.add(PermissionAttachmentInfo(this, it, null, true))
        }

        return infos
    }

    private fun validatePlayer(): SickPlayer {
        var cachePlayer: SickPlayer? = null
        databaseScope.launch {
            cachePlayer = SickPlayers.getSickPlayer(player.uniqueId)
        }
        return cachePlayer!!
    }

}