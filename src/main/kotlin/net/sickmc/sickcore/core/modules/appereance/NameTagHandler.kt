package net.sickmc.sickcore.core.modules.appereance

import kotlinx.coroutines.runBlocking
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.axay.kspigot.event.listen
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.DisplaySlot

class NameTagHandler {

    private val attributes = HashMap<Player, List<Component>>()

    fun handleNameTags(){
        listen<PlayerJoinEvent> {
            attributes[it.player] = getPlayerAttributes(it.player)
            it.player.scoreboard.registerNewObjective("playerStats", "001", attributes[it.player]?.shuffled()?.get(1)).displaySlot = DisplaySlot.BELOW_NAME
            it.player.scoreboard.getObjective("playerStats")?.displayName(attributes[it.player]?.shuffled()?.get(1))
            updateAttributes(it.player)
        }
    }

    private fun updateAttributes(player: Player){
        task(period = 60, delay = 60){
            player.scoreboard.getObjective("playerStats")?.displayName(attributes[player]?.shuffled()?.get(1))
        }
    }

    private fun getPlayerAttributes(player: Player): List<Component>{
        val attributes = ArrayList<Component>()
        runBlocking {
            val sickPlayer = SickPlayers.getSickPlayer(player.uniqueId)
            attributes.add(MiniMessage.miniMessage().deserialize("<gradient:#d7b728:#d7a328>Addiction: </gradient><gradient:#1fc3e0:#19a0b8>${sickPlayer?.addiction.toString()}</gradient>"))
            attributes.add(MiniMessage.miniMessage().deserialize("<gradient:#d7b728:#d7a328>Exp: </gradient><gradient:#1fc3e0:#19a0b8>${sickPlayer?.exp.toString()}</gradient>"))
        }

        return attributes.toList()
    }

}