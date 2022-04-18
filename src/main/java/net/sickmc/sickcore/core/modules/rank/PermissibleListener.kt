package net.sickmc.sickcore.core.modules.rank

import net.sickmc.sickcore.utils.ReflectionUtils
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerLoginEvent
import java.lang.reflect.Field

class PermissibleListener {

    init {
        handlePlayerJoin()
    }

    private fun handlePlayerJoin(){
        listen<PlayerLoginEvent> {
            try {
                val clazz: Class<*>? = ReflectionUtils.reflectCraftClazz(".entity.CraftHumanEntity")
                var field: Field? = null
                if (clazz != null) {
                    field = clazz.getDeclaredField("perm")
                }
                if (field == null) {
                    println("WARNING: Permission field was null")
                    return@listen
                }
                field.isAccessible = true
                field[it.player] = SickPermissibleBase(it.player)
            } catch (ex: NoSuchFieldException) {
                ex.printStackTrace()
            } catch (ex: SecurityException) {
                ex.printStackTrace()
            } catch (ex: IllegalArgumentException) {
                ex.printStackTrace()
            } catch (ex: IllegalAccessException) {
                ex.printStackTrace()
            }
        }
    }

}