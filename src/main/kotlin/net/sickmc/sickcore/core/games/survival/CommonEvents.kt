package net.sickmc.sickcore.core.games.survival

import net.axay.kspigot.event.listen
import org.bukkit.event.entity.EntityDeathEvent

object CommonEvents {

    init {

    }

    val mobDrops = listen<EntityDeathEvent> {
        if (it.entity.killer == null)return@listen

    }

}