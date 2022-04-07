package me.anton.sickcore.utils.paper

import kotlinx.coroutines.launch
import me.anton.sickcore.core.Core
import me.anton.sickcore.core.player.SickPlayer
import me.anton.sickcore.core.player.SickPlayers
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class InventoryBuilder(val player: SickPlayer, private val height: Int, val name: Component) {

    val interactions = HashMap<Int, (InventoryClickEvent) -> Unit>()
    val inventory = Bukkit.createInventory(null, 9 * height, name)

    init {
        InventoryBuilders.addInventory(this)
    }

    fun setItem(slot: Int, item: ItemBuilder, event: (InventoryClickEvent) -> Unit){
        inventory.setItem(slot, item.build())
        interactions[slot] = event
    }

    fun addItem(item: ItemBuilder, event: (InventoryClickEvent) -> Unit){
        inventory.addItem(item.build())
        interactions[inventory.first(item.build())]
    }

    fun fillEmptyPlaceholders(){
        for (int in 0 until inventory.size){
            if (inventory.getItem(int) == null)setPlaceholder(int)
        }
    }

    fun setPlaceholder(vararg slot: Int){
        val builder = ItemBuilder(player, Material.GRAY_STAINED_GLASS_PANE).setName(null).addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
        slot.forEach {
            setItem(it, builder) { event -> event.isCancelled = true }
        }
    }

    fun open(){
        val bukkitPlayer = Bukkit.getPlayer(player.uuid)

        bukkitPlayer?.openInventory(inventory)
    }

}

class InventoryBuilders{

    companion object{
        val inventorys = HashMap<SickPlayer, ArrayList<InventoryBuilder>>()

        fun addInventory(inventory: InventoryBuilder){
            val builders = inventorys[inventory.player]
            builders?.add(inventory)
            inventorys.replace(inventory.player, builders!!)
        }
    }

    fun registerInventoryHandlers(){
        listen<InventoryClickEvent> {
            Core.instance.databaseScope.launch {
                if (it.currentItem == null)return@launch
                if (!inventorys.containsKey(SickPlayers.getSickPlayer((it.whoClicked as Player).uniqueId)))return@launch
                val inventorys = inventorys[SickPlayers.getSickPlayer((it.whoClicked as Player).uniqueId)]
                val bukkitInventorys = ArrayList<Inventory>()
                inventorys?.forEach { bukkitInventorys.add(it.inventory) }
                if (!bukkitInventorys.contains(it.inventory))return@launch
                var inventory: InventoryBuilder? = null
                inventorys?.forEach{builder ->
                    if (builder.inventory == it.inventory)inventory = builder
                }
                if (inventory?.interactions?.get(it.slot) == null)return@launch
                inventory!!.interactions[it.slot]!!.invoke(it)
                TODO("add click sound")
            }
        }
    }

}