package net.sickmc.sickcore.core.modules.appereance

import kotlinx.coroutines.runBlocking
import net.sickmc.sickcore.core.player.SickPlayers
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Bat
import org.bukkit.entity.EntityType
import org.bukkit.entity.Pig
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

class NameTagHandler {

    val players = HashMap<Player, ArmorStand>()

    fun handleNameTags(){
        listen<PlayerJoinEvent> {
            val attributes = getPlayerAttributes(it.player)

            players[it.player] = it.player.world.spawnEntity(it.player.eyeLocation.add(0.0, 0.5,0.0), EntityType.ARMOR_STAND) as ArmorStand
            players[it.player]?.setAI(false)
            players[it.player]?.setGravity(false)
            players[it.player]?.isMarker = true
            players[it.player]?.isCustomNameVisible = true
            players[it.player]?.isCollidable = false
            players[it.player]?.isInvulnerable = true
            players[it.player]?.isPersistent = false
            players[it.player]?.removeWhenFarAway = true
            players[it.player]?.isInvisible = true
            players[it.player]?.customName(attributes.shuffled()[1])
            val pig = it.player.world.spawnEntity(it.player.eyeLocation.add(0.0,0.5,0.0), EntityType.PIG) as Pig
            pig.setGravity(false)
            pig.setAI(false)
            pig.isCustomNameVisible = false
            pig.isCollidable = false
            pig.isInvulnerable = true
            pig.isInvisible = true
            pig.isPersistent = false
            pig.removeWhenFarAway = false
            pig.addPassenger(players[it.player]?: return@listen)
            it.player.addPassenger(pig)
        }

        listen<PlayerMoveEvent> {
            if (it.to == it.from)return@listen
        }

        listen<EntityDamageEvent> {
            if (it.entity !is Bat)return@listen
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