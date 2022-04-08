package me.anton.sickcore.core.modules.rank

import com.velocitypowered.api.event.permission.PermissionsSetupEvent
import com.velocitypowered.api.permission.PermissionFunction
import com.velocitypowered.api.permission.PermissionProvider
import com.velocitypowered.api.permission.PermissionSubject
import com.velocitypowered.api.permission.Tristate
import com.velocitypowered.api.proxy.Player
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.VelocityCore
import me.anton.sickcore.core.listenVelocity
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.mongo.databaseScope

class VelocityPermissionProvider : PermissionProvider{

    init {
        listenVelocity<PermissionsSetupEvent> {
            it.provider = this
        }
    }

    override fun createFunction(subject: PermissionSubject?): PermissionFunction {
        if (subject !is Player)return PermissionFunction.ALWAYS_TRUE
        return SickPermissionFunction(subject)
    }

    private class SickPermissionFunction(val player: Player): PermissionFunction{

        override fun getPermissionValue(permission: String?): Tristate {
            if (permission == null)return Tristate.FALSE
            return Tristate.fromBoolean(getPermissions().contains(permission))
        }

        fun getPermissions(): List<String>{
            val list = ArrayList<String>()
            databaseScope.launch {
                list.addAll(SickPlayers.getSickPlayer(player.uniqueId)!!.getPermissions())
            }
            return list.toList()
        }

    }

}