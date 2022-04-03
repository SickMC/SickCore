package me.anton.sickcore.core.modules.appereance

import me.anton.sickcore.utils.paper.RankUpdateEvent
import net.axay.kspigot.event.listen
import org.bukkit.Bukkit

class CommandSuggestionHandler {

    fun handlePaperCommands(){
        listen<RankUpdateEvent> {
            Bukkit.reloadPermissions()
            Bukkit.reloadCommandAliases()
        }
    }

}
