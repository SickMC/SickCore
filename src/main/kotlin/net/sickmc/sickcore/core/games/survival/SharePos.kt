package net.sickmc.sickcore.core.games.survival

import net.axay.kspigot.commands.argument
import net.axay.kspigot.commands.command
import org.bukkit.command.CommandExecutor

class SharePos : CommandExecutor {

   val sharepos = command("sharepos"){
        argument<String>("targetPlayer") {player ->

        }
   }