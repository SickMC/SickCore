package net.sickmc.sickcore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.axay.fabrik.core.Fabrik
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.chat.Component
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.games.survival.SurvivalPlayers
import net.sickmc.sickcore.utils.EventManager
import net.sickmc.sickcore.utils.test
import net.sickmc.sickcore.utils.fabric.sendMessage
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.redis.kreds
import net.sickmc.sickcore.utils.redis.subscribeRedis
import java.util.*

val fabricScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

class FabricManager : ModInitializer {

    companion object{
        lateinit var instance: FabricManager
    }

    override fun onInitialize() {
        instance = this

        val moduleHandler = ModuleHandler(environment)
        databaseScope.launch {
            if (!test) kreds.auth(System.getenv("REDIS_PASSWORD")) else kreds.auth(System.getProperty("REDIS_PASSWORD"))
            registerCommands()
            registerCaches()
            EventManager.register()
            moduleHandler.start()
            handleSickPlayers()
            Game.enable()
        }

        ServerLifecycleEvents.SERVER_STOPPING.register{
            fabricScope.launch {
                moduleHandler.shutdown()
            }
        }
    }

    private fun registerCommands(){

    }

    private fun registerCaches(){
        SurvivalPlayers()
        SickPlayers()
    }

    private suspend fun handleSickPlayers(){
        ServerPlayConnectionEvents.INIT.register{ handler, _->
            if (handler.player == null)return@register
            databaseScope.launch {
                SickPlayers.instance.reloadEntity(handler.player!!.uuid)
            }
        }
        /*subscribeRedis("message"){
            val uuid = UUID.fromString(it.split('/')[0])
            val component = Component.Serializer.fromJson(it.split('-')[1])
            Fabrik.currentServer?.playerList?.getPlayer(uuid)?.sendMessage(component!!)
        }

         */
    }

}