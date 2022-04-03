package me.anton.sickcore.core.modules.appereance

import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.player.SickPlayers
import net.axay.kspigot.event.listen
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Bat
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class NameTagHandler {

    val players = HashMap<Player, Bat>()

    fun handleNameTags(){
        listen<PlayerJoinEvent> {
            var attributes: List<String>? = null
            Core.instance.databaseScope.launch {
                attributes = getPlayerAttributes(it.player)
            }
            players[it.player] = it.player.world.spawnEntity(it.player.eyeLocation, EntityType.BAT) as Bat
            players[it.player]?.setAI(false)
            players[it.player]?.isInvisible = true
            players[it.player]?.customName(MiniMessage.miniMessage().deserialize(attributes?.shuffled()?.get(1) ?: ""))
            startNameTagRunnable(it.player)
        }

        listen<PlayerMoveEvent> {
            players[it.player]?.teleport(it.player.eyeLocation)
        }
    }

    fun startNameTagRunnable(player: Player){
        task(sync = false, delay = 3000){
            var attributes: List<String>? = null
            Core.instance.databaseScope.launch {
                attributes = getPlayerAttributes(player)
            }
            players[player]?.customName(MiniMessage.miniMessage().deserialize(attributes?.shuffled()?.get(1) ?: ""))
        }
    }

    suspend fun getPlayerAttributes(player: Player): List<String>{
        val sickPlayer = SickPlayers.getSickPlayer(player.uniqueId)
        val attributes = ArrayList<String>()
        attributes.add("<gradient:#d7b728:#d7a328>Addiction: </gradient><gradient:#1fc3e0:#19a0b8>${sickPlayer?.addiction.toString()}</gradient>")
        attributes.add("<gradient:#d7b728:#d7a328>Exp: </gradient><gradient:#1fc3e0:#19a0b8>${sickPlayer?.exp.toString()}</gradient>")
        return attributes
    }

}