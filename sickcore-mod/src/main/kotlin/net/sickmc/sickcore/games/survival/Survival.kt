package net.sickmc.sickcore.games.survival

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.enchantment.EnchantmentCategory
import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.server
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.*
import net.silkmc.silk.core.text.literalText

val extraHeads = arrayListOf(
    EntityAttributes(
        "Ice Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYzNDJjYmUyOGJlMjBjY2ZjNjhhZTUwYjg4ZGVlOWIzNzM4NzQwOTBjYzcwNzk3ZjNmZmNkYzAwYTgyZTRjIn19fQ==",
        MobHeadRarity.SPECIAL
    ),
    EntityAttributes(
        "Hooded Enderman",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYxMWI4MWI3ODRhNzc0NTlhOWY5ZDI1MDk0YjhiMmUzZDg5MDI4ZTFlN2JiYWJlOTE2NjVjZDJkYzY2NiJ9fX0=",
        MobHeadRarity.SPECIAL
    ),
    EntityAttributes(
        "Paimon",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM1MWNlOWE2NjRkNThmN2JhOTM2NTIyOTQzMGMzMDNkMTYwMjM3NjFiNzBkZDExZTM3YWE5NjVjZGZmYjQ3YiJ9fX0=",
        MobHeadRarity.SPECIAL
    ),
    EntityAttributes(
        "Herobrine",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=",
        MobHeadRarity.MYTHIC
    )
)

class Survival : Game() {

    override val name: String = "Survival"
    override val startEnvironment: Array<String> = arrayOf("public_survival_latest")

    val telekinesis = arrayListOf<Enchantment>()
    override fun preEnable() {
        val telekinesisCategories = arrayListOf(
            EnchantmentCategory.DIGGER,
            EnchantmentCategory.WEAPON,
            EnchantmentCategory.BOW,
            EnchantmentCategory.CROSSBOW
        )
        telekinesisCategories.forEach {
            telekinesis.add(
                Registry.register(
                    Registry.ENCHANTMENT,
                    ResourceLocation("sickcore", "telekinesis_${it.name.lowercase()}"),
                    Telekinesis(it)
                )
            )
        }
        SurvivalCommands.register()
    }

    override suspend fun enable() {
        CommonEvents.register()
        tablist.server = server
    }

    override val tablist: Tablist = Tablist() {
        playerTabListBuilder(this) {
            val sickPlayer = player.sickPlayer!!
            prefix = literalText(sickPlayer.rank.getParent().prefix)
            color = sickPlayer.rank.getParent().color
            header = literalText("headdderrrr") { color = Colors.BLACK }
            footer = literalText("fooooterrr") { color = Colors.WHITE }
        }
    }

    override suspend fun disable() {

    }

}