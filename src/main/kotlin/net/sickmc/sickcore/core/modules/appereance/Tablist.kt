package net.sickmc.sickcore.core.modules.appereance

import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.paper.RankUpdateEvent
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.sickmc.sickcore.utils.paper.mm
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class Tablist {

    suspend fun reloadTablist(){
        Bukkit.getOnlinePlayers().forEach {
            val sickPlayer = SickPlayers.getSickPlayer(it.uniqueId) ?: return@forEach
            val list = it.scoreboard

            var team = list.getTeam(sickPlayer.rank.parent.priority.toString() + sickPlayer.name)
            if (team == null)team = list.registerNewTeam(sickPlayer.rank.parent.priority.toString() + sickPlayer.name)
            team.prefix(mm.deserialize(sickPlayer.rank.parent.coloredPrefix + "<gradient:#5e5e5e:#5e5e5e>× </gradient>"))
            team.suffix(mm.deserialize(sickPlayer.rank.parent.color))
            team.color(TextColor.fromCSSHexString(sickPlayer.rank.parent.color)?.let { it1 -> NamedTextColor.nearestTo(it1) })
            team.addPlayer(it)
            it.displayName(sickPlayer.displayName)
        }
    }

    suspend fun hardReloadTablist(){
        Bukkit.getOnlinePlayers().forEach {
            val sickPlayer = SickPlayers.reloadPlayer(it.uniqueId) ?: return@forEach
            val list = it.scoreboard

            var team = list.getTeam(sickPlayer.rank.parent.priority.toString() + sickPlayer.name)
            if (team == null)team = list.registerNewTeam(sickPlayer.rank.parent.priority.toString() + sickPlayer.name)
            team.prefix(MiniMessage.miniMessage().deserialize(sickPlayer.rank.parent.coloredPrefix + "<gradient:#5e5e5e:#5e5e5e>× </gradient>"))
            team.suffix(MiniMessage.miniMessage().deserialize(sickPlayer.rank.parent.color))
            team.color(TextColor.fromCSSHexString(sickPlayer.rank.parent.color)
                ?.let { it1 -> NamedTextColor.nearestTo(it1) })

            team.addPlayer(it)
            it.displayName(sickPlayer.displayName)
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
                tablist.hardReloadTablist()
            }
        }
    }


}