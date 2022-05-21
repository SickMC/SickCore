package net.sickmc.sickcore.core.games.survival

import kotlinx.coroutines.launch
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.paper.getHead
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import kotlin.random.Random
import kotlin.random.nextInt

object CommonEvents {

    fun register(){
        mobDrops.register()
        join.register()
    }

    val join = listen<PlayerJoinEvent> {
        databaseScope.launch {
            SurvivalPlayers.instance.reloadEntity(it.player.uniqueId)
        }
    }

    private val mobDrops = listen<EntityDeathEvent> {
        if (it.entity.killer == null)return@listen
        if (it.entity.getHead().isOnlyCreative || it.entity.getHead().isNaturalAvailable)return@listen
        val killer = it.entity.killer!!
        if (Random.nextInt(1 .. 64) == 1){
            databaseScope.launch {
                val gamePlayer = SurvivalPlayers.instance.getCachedEntity(killer.uniqueId)!!
                gamePlayer.addHead(it.entity)
                SurvivalPlayers.instance.reloadEntity(killer.uniqueId)
            }
        }
    }

}