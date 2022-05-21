package net.sickmc.sickcore.utils.paper.item

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.sickmc.sickcore.core.commonPlayer.SickPlayer
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.axay.kspigot.event.listen
import net.axay.kspigot.items.name
import net.kyori.adventure.text.Component
import net.sickmc.sickcore.utils.paper.mm
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

inline fun itemBuilder(material: Material, player: SickPlayer, crossinline builder: ItemBuilder.() -> Unit){}

class ItemBuilder(private val material: Material, val player: SickPlayer): ItemStack(material) {

    companion object{
        val interactions = HashMap<SickPlayer, List<ItemBuilder>>()

        fun register(){
            listen<PlayerInteractEvent> {
                if (!it.hasItem())return@listen
                val player = SickPlayers.instance.getCachedEntity(it.player.uniqueId)
                if (!interactions.containsKey(player))return@listen
                if (!interactions[player]!!.contains(it.item))return@listen
                interactions[player]?.forEach { item ->
                    if (it.item != item)return@forEach
                    item.clickEvent!!.invoke(it)
                }
            }
        }
    }

    var clickEvent: (PlayerInteractEvent.() -> Unit)? = null
    var name: Component? = null
    var rawLore: ArrayList<Component>? = null
    var lore: ArrayList<Component>? = null
    var itemFlags: ArrayList<ItemFlag>? = null
    var glowing = false
    var enchantments: HashMap<Enchantment, Int>? = null
    var damage: Int = 0
    var unbreakable = false
    var skullOwner: UUID? = null
    var skullTexture: String? = null
    var leatherArmorColor: Color? = null

    fun nameSpacedKey(key: NamespacedKey, type: PersistentDataType<Any, Any>, value: Any){
        itemMeta.persistentDataContainer.set(key, type, value)
    }

    fun clickEvent(event: PlayerInteractEvent.() -> Unit){
        clickEvent = event
    }

    fun build(){
        itemMeta.name = name ?: Component.text("§7")
        if (lore != null){
            lore!!.forEach {
                lore!!.remove(it)
                lore!!.add(mm.deserialize("<italic> ${mm.serialize(it)} </italic>"))
            }
            itemMeta.lore(lore)
        }
        if (rawLore != null)lore(rawLore)
        itemFlags?.forEach{itemMeta.addItemFlags(it)}
        if (glowing)itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true)
        enchantments?.forEach { itemMeta.addEnchant(it.key, it.value, true) }
        (itemMeta as Damageable).damage = damage
        itemMeta.isUnbreakable = unbreakable
        if (skullOwner != null) (itemMeta as SkullMeta).owningPlayer = Bukkit.getOfflinePlayer(skullOwner!!)
        if (skullTexture != null){
            val profile = GameProfile(UUID.randomUUID(), null)
            val encoded = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", skullTexture).toByteArray())
            profile.properties.put("textures", Property("textures", String(encoded)))
            val field: Field
            val skullMeta = itemMeta as SkullMeta

            field = skullMeta.javaClass.getDeclaredField("profile")

            assert(field != null)
            field.isAccessible = true
            field.set(skullMeta, profile)
        }
        if (leatherArmorColor != null) (itemMeta as LeatherArmorMeta).setColor(leatherArmorColor)
        if (clickEvent != null){
            if (!interactions.containsKey(player)) interactions[player] = arrayListOf(this)
            else {
                val newBuilders = interactions[player] as ArrayList
                newBuilders.add(this)
                interactions[player] = newBuilders
            }
        }
    }

}