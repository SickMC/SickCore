package net.sickmc.sickcore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.server.MinecraftServer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.games.survival.SurvivalPlayers
import net.sickmc.sickcore.games.survival.preGen
import net.sickmc.sickcore.utils.mongo.databaseScope

val fabricScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
lateinit var server: MinecraftServer

class FabricManager : ModInitializer {

    companion object{
        lateinit var instance: FabricManager
    }

    override fun onInitialize() {
        instance = this

        val moduleHandler = ModuleHandler(environment)

        registerCaches()

        fabricScope.launch {
            handleSickPlayers()
            moduleHandler.start()
        }

        registerCommands()
        registerCaches()
        Game.preEnable()

        ServerLifecycleEvents.SERVER_STARTED.register{
            server = it
            fabricScope.launch {
                Game.enable()
            }
            //handleTablist()
            it.commands.performCommand(it.createCommandSourceStack(), "chunky continue")
            preGen = true
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

    private fun handleSickPlayers(){
        ServerPlayConnectionEvents.INIT.register{ handler, _->
            if (handler.player == null)return@register
            databaseScope.launch {
                SickPlayers.instance.reloadEntity(handler.player!!.uuid)
            }
        }
    }

    private fun handleTablist(){
        ServerPlayConnectionEvents.JOIN.register{ _, _, _ ->
            Game.current.tablist.reloadAll()
        }

        ServerPlayConnectionEvents.DISCONNECT.register{ _, _ ->
            Game.current.tablist.reloadAll()
        }
    }

}