package net.sickmc.sickcore.core.modules.rank

import com.velocitypowered.api.event.permission.PermissionsSetupEvent
import com.velocitypowered.api.permission.PermissionFunction
import com.velocitypowered.api.permission.PermissionProvider
import com.velocitypowered.api.permission.PermissionSubject
import com.velocitypowered.api.permission.Tristate
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.listenVelocity
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope

class VelocityPermissionProvider : PermissionProvider{

    init {
        listenVelocity<PermissionsSetupEvent> {
            if (it.subject is Player){
                databaseScope.launch { SickPlayers.instance.reloadEntity((it.subject as Player).uniqueId) }
            }
            it.provider = this
        }
    }

    override fun createFunction(subject: PermissionSubject?): PermissionFunction {
        if (subject !is Player)return PermissionFunction.ALWAYS_TRUE
        return SickPermissionFunction(subject)
    }

    private class SickPermissionFunction(val player: Player): PermissionFunction{

        private var sickPlayer = SickPlayers.instance.getCachedEntity(player.uniqueId)!!

        init {
            identifyPlayer()
        }

        private var permissions = sickPlayer.permissions
        private val admin = identifyAdmin()

        override fun getPermissionValue(permission: String?): Tristate {
            if (permission == null)return Tristate.UNDEFINED
            if (admin) return Tristate.TRUE
            return Tristate.fromBoolean(permissions.contains(permission))
        }

        fun identifyAdmin(): Boolean{
            return sickPlayer.rank.getParent().name == "Administration"
        }

        private fun identifyPlayer(){
            databaseScope.launch {
                sickPlayer = SickPlayers.instance.getCachedEntity(player.uniqueId) ?: error("Failed to load sickPlayer ${player.uniqueId}")
            }
        }

    }

}