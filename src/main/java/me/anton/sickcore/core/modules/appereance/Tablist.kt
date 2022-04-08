package me.anton.sickcore.core.modules.appereance

import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.PaperCore
import me.anton.sickcore.core.player.SickPlayers
import me.anton.sickcore.utils.mongo.databaseScope
import me.anton.sickcore.utils.paper.RankUpdateEvent
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Tablist {

    suspend fun reloadTablist(){
        PaperCore.instance?.onlinePlayers?.forEach {
            val sickPlayer = SickPlayers.getSickPlayer(it.uniqueId) ?: return@forEach
            val list = it.scoreboard

            var team = list.getTeam(sickPlayer.getRank().getParent().priority.toString() + sickPlayer.name)
            if (team == null)team = list.registerNewTeam(sickPlayer.getRank().getParent().priority.toString() + sickPlayer.name)
            team.prefix(MiniMessage.miniMessage().deserialize(sickPlayer.getRank().getParent().coloredPrefix + "<gradient:#5e5e5e:#5e5e5e>× </gradient>"))
            team.suffix(MiniMessage.miniMessage().deserialize(sickPlayer.getRank().getParent().color))
            team.color(TextColor.fromCSSHexString(sickPlayer.getRank().getParent().color)
                ?.let { it1 -> NamedTextColor.nearestTo(it1) })

            team.addPlayer(it)
            it.displayName(sickPlayer.getDisplayname())
        }
    }

    suspend fun hardReloadTablist(){
        PaperCore.instance?.onlinePlayers?.forEach {
            val sickPlayer = SickPlayers.reloadPlayer(it.uniqueId) ?: return@forEach
            val list = it.scoreboard

            var team = list.getTeam(sickPlayer.getRank().getParent().priority.toString() + sickPlayer.name)
            if (team == null)team = list.registerNewTeam(sickPlayer.getRank().getParent().priority.toString() + sickPlayer.name)
            team.prefix(MiniMessage.miniMessage().deserialize(sickPlayer.getRank().getParent().coloredPrefix + "<gradient:#5e5e5e:#5e5e5e>× </gradient>"))
            team.suffix(MiniMessage.miniMessage().deserialize(sickPlayer.getRank().getParent().color))
            team.color(TextColor.fromCSSHexString(sickPlayer.getRank().getParent().color)
                ?.let { it1 -> NamedTextColor.nearestTo(it1) })

            team.addPlayer(it)
            it.displayName(sickPlayer.getDisplayname())
        }
    }

}

class TablistProvider {

    val tablist = Tablist()

    fun handleTablist(){
        listen<PlayerJoinEvent> {
            databaseScope.launch {
                tablist.reloadTablist()
            }
        }

        listen<PlayerQuitEvent> {
            databaseScope.launch {
                tablist.reloadTablist()
            }
        }

        listen<RankUpdateEvent> {
            databaseScope.launch {
                tablist.reloadTablist()
            }
        }
    }


}