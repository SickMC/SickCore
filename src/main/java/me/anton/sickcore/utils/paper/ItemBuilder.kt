package me.anton.sickcore.utils.paper

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.player.SickPlayer
import me.anton.sickcore.core.player.SickPlayers
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Damageable
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.litote.kmongo.bson
import java.lang.reflect.Field
import java.util.Base64
import java.util.UUID

class ItemBuilder(val player: SickPlayer, val material: Material) {

    var clickEvent: ((PlayerInteractEvent) -> Unit)? = null
    var itemStack = ItemStack(material)
    var itemMeta: ItemMeta = itemStack.itemMeta

    init {
        ItemBuilders.addItem(this)
    }

    fun setName(component: Component?): ItemBuilder{
        if (component == null){
            itemMeta.displayName(Component.text("§7"))
            return this
        }
        itemMeta.displayName(component)
        return this
    }

    fun addUnformattedLore(vararg component: Component): ItemBuilder{
        itemMeta.lore(component.asList())

        return this
    }

    fun addLore(vararg component: Component): ItemBuilder{
        val lores = ArrayList<Component>()
        component.forEach {
            lores.add(MiniMessage.miniMessage().deserialize("<italic> ${MiniMessage.miniMessage().serialize(it)} </italic>"))
        }

        itemMeta.lore(lores.toMutableList())
        return this
    }

    fun addItemFlag(vararg flag: ItemFlag):ItemBuilder{
        flag.forEach { itemMeta.addItemFlags(it) }
        return this
    }

    fun glowing(value: Boolean): ItemBuilder{
        addEnchantment(Enchantment.ARROW_FIRE, 1)
        addItemFlag(ItemFlag.HIDE_ENCHANTS)
        return this
    }

    fun addEnchantment(enchantment: Enchantment, level: Int):ItemBuilder{
        itemMeta.addEnchant(enchantment, level, true)
        return this
    }

    fun setAmount(amount: Int): ItemBuilder{
        itemStack.amount = amount
        return this
    }

    fun asClickableItem(interaction: (PlayerInteractEvent) -> Unit): ItemBuilder{
        clickEvent = interaction
        return this
    }

    fun setDurability(damage: Int): ItemBuilder{
        val damageable = itemMeta as Damageable
        damageable.damage(damage.toDouble())
        return this
    }

    fun setUnbreakable(value: Boolean): ItemBuilder{
        itemMeta.isUnbreakable = value
        return this
    }

    fun setSkull(owner: UUID): ItemBuilder{
        if (itemStack.type != Material.PLAYER_HEAD)return this
        val meta = itemMeta as SkullMeta
        meta.owningPlayer = Bukkit.getOfflinePlayer(owner)
        return this
    }

    fun setSkull(base64: String): ItemBuilder{
        if (itemStack.type != Material.PLAYER_HEAD)return this
        val profile = GameProfile(UUID.randomUUID(), null)
        val encoded = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", base64).toByteArray())
        profile.properties.put("textures", Property("textures", String(encoded)))
        var field:Field? = null
        val skullMeta = itemMeta as SkullMeta

        field = skullMeta.javaClass.getDeclaredField("profile")

        assert(field != null)
        field.isAccessible = true
        field.set(skullMeta, profile)

        return this
    }

    fun setLeatherArmorColor(color: Color): ItemBuilder{
        val meta = itemMeta as LeatherArmorMeta
        meta.setColor(color)
        return this
    }

    fun addNameSpacedKey(key: NamespacedKey, type: PersistentDataType<Any, Any>, value: Any):ItemBuilder{
        itemMeta.persistentDataContainer.set(key, type, value)

        return this
    }

    fun build(): ItemStack{
        itemStack.itemMeta = itemMeta

        return itemStack
    }

}

class ItemBuilders{
    companion object{
        val items = HashMap<SickPlayer, ArrayList<ItemBuilder>>()

        fun addItem(builder: ItemBuilder){
            val builders = items[builder.player]
            builders?.add(builder)
            items.replace(builder.player, builders!!)
        }
    }

    fun registerBuilders(){
        listen<PlayerInteractEvent> {
            Core.instance.databaseScope.launch {
                if (!it.hasItem())return@launch
                if (!items.containsKey(SickPlayers.getSickPlayer(it.player.uniqueId)))return@launch
                val items = items[SickPlayers.getSickPlayer(it.player.uniqueId)]
                val itemStacks = ArrayList<ItemStack>()
                items?.forEach { itemStacks.add(it.itemStack) }
                if (!itemStacks.contains(it.item))return@launch
                var item: ItemBuilder? = null
                items?.forEach{builder ->
                    if (builder.itemStack == it.item)item = builder
                }
                item?.clickEvent?.invoke(it)
            }
        }
    }
}