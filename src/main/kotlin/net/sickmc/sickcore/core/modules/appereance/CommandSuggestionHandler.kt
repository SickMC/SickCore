package net.sickmc.sickcore.core.modules.appereance

import net.sickmc.sickcore.utils.fabric.RankUpdateEvent
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
