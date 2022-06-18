package net.sickmc.sickcore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.games.survival.SurvivalPlayers
import net.sickmc.sickcore.utils.EventManager
import net.sickmc.sickcore.utils.mongo.databaseScope

val fabricScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

class FabricManager : ModInitializer {

    companion object{
        lateinit var instance: FabricManager
    }

    override fun onInitialize() {
        instance = this

        val moduleHandler = ModuleHandler(environment)
        databaseScope.launch {
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
    }

}