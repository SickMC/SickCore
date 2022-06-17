package net.sickmc.sickcore.utils.fabric

import net.axay.fabrik.core.item.itemStack
import net.axay.fabrik.core.item.setSkullTexture
import net.axay.fabrik.core.text.literalText
import net.axay.fabrik.nbt.dsl.nbtCompound
import net.axay.fabrik.nbt.dsl.nbtList
import net.axay.fabrik.nbt.toNbt
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.*
import net.minecraft.world.entity.animal.axolotl.Axolotl
import net.minecraft.world.entity.animal.frog.Frog
import net.minecraft.world.entity.animal.horse.Horse
import net.minecraft.world.entity.animal.horse.Llama
import net.minecraft.world.entity.animal.horse.TraderLlama
import net.minecraft.world.entity.monster.ZombieVillager
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import java.util.*

data class MobHead(
    val isOnlyCreative: Boolean,
    val isNaturalAvailable: Boolean,
    val item: ItemStack,
    val key: EntityKey,
    val attributes: EntityAttributes
)

data class EntityKey(
    val type: EntityType<*>,
    val variant: Int? = null,
    val color: Int? = null,
    val id: String? = null,
    val catVariant: CatVariant? = null,
    val frogVariant: FrogVariant? = null
)

data class EntityAttributes(val name: String, val texture: String, val rarity: MobHeadRarity = MobHeadRarity.RARE) {

    fun getDisplayName(): MutableComponent {
        return literalText(name) {
            color = rarity.color
        }
    }

}

enum class MobHeadRarity(val color: Int) {

    RARE(0xEBDC20),
    SPECIAL(0x8D195F),
    MYTHIC(0xD39716)

}

val textures = mapOf(
    //Cat
    EntityKey(EntityType.CAT, null, 0, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IzNTViYTM4OTAxZWU4NmEyM2Q2MWU5Y2UxMDE2NThmMjQxN2Q2YjliNGMzODEyYTkwNGFkZTU4MWNiNDQxNSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWY2OWZhNTgzYWNjMTIyMzYyNDFkOWM1YTEyZjA0YzFmM2E1OTMyMDNhZjU1OTBkODIxMzM4MDViYWZmMTQzNyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjNjM2ZDc0Yjc0YWQ2ZTU0MDJjNmQyZDFmZTg1MjI0ZTgwZGQxZDk3N2MxMDYwMzE5ZGFhOTdiNmI2YmIwZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEwZjBiMWQ0Njk5OGRjZGE2ZTM3MDEzMTZhMjg1YjZiYjNiNGIwNDVhZDFkNDhjOGRiMmYwNDhhZmY1MDI0ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVhYTNhNTA2ZmIzOTk0ZTUxMjU1NDVkYjMyM2RjNTVhNzg5MjE4MzY4NGE1YWNhOWY4OTA2ZWMzN2RjZTMzMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU4YjQzMGZmZTI4YjRhMDUzODhjM2I3NDllZDFlYWI2M2JlMTQ4NjFlZWNhYTRlNzAzYjhlZmY4N2Y1ZjM4MyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmIwYjgwNmQzOTMwMjA4YTk0MWQ4MGQ4NDM2NDllOTYyODBiY2U4ZTdkN2YzNmFhODE5ZGU3MWE4MWQ1OGIwZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWVhNWEyZjZkNzcxMTIxN2E3Mzg4ZWVhMzgzZTFmMmIyNmU2MDI3NWM5Mzg0MzdiYmFmZTNjNDE5ZWEyY2YxZCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE4OGI0ZDIzNGRmYTM3Y2ZlODM0YjdlOWUzNTMxMzRlMGQ1NmM5NzUyOTAwMmNkNGYzNGMzMTE0ZmRkZGVmNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmZkZWQyYmJjZjExOGIxMTBiM2Y5MWExYTZjMDc0YzRhZmMyYjcwNTkyOGViNDZlM2Q0YmFhZGZiNDI2ODZlYSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQyOTI2YTY5NzZmMDU3MjVmZDBhYzEwNzlhYmVhZDQ5NDI3NzQ3YTY4NzkyOWVmZTMxZTFjY2RiY2ZhNzQxZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGJkNzExYzljNWJlMTM3Njk4MjQzYWUxZDU4NzczYmNjZTNiMDE5MjVjNzczZTk3YTM4MmVkYzhiMTBhYWFhOCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU4ZmVjOGIwMWMxYjYxZDI4N2U2Njc4MTBmYTBkNDQ0ZTFkZGQ4MGU3NGQ3MDEwMDg2MTVhMTMxZjY1MzcxZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYwMjQ1YmUxODMwZjRjY2NhZjFjYmI1ZmVhNDVlMmRlMDFiY2Q4MmFiNzZmMTAxMDA4MGJjZDA0ZWJjYjJmMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjlkNDRjYTVhNTVkODdlNDM5NDc1YmYyNmUzNzQ3ZjRmNTkzY2YyMDVhNWEyNzEzY2E0ODY3ZjA2ZmM0MGFiNSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.BLACK) to EntityAttributes(
        "Black Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNWUwNGY5MWRkNzY4ZjYwNTBiMmFkNDc3ZTY0MmVlY2UxMDRmNWU5YzNhZmNlNGY3NTQ3ZGRhMWYyNTYyMSJ9fX0="
    ),

    EntityKey(
        EntityType.CAT,
        null,
        0,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY1MzkyOTNlYmVhYmE0YzM1ZjA4OTM1N2U1NDU3ZjgzOGZhNGE1ZGI0ZGE4M2FmYmZjMjk1YWQyZDZkNmVhNiJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        1,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRhZmU4M2U0ZjU3ODhlNzY5MTQ0NmIzMTUxNmE4NWQxZjVmMjdjYTAwYTM1ZDIyNzk1M2EwMjcwOWZkMmY2ZCJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        2,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRiNTcyMTBiNGRjNWRkMTk5NjI0NTA2NTcwNDEwMjcyMzkyYjhiYTQ1YWZlYzY2NDlkOTQ2ZWJiNWRlMDg2OCJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        3,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNhMTNiMzZjYWE1ZDUzYTRmMjNiOThlYzAyOThjOGJhMTJkNDljMWJkNWI2YzY4YTZjMDY5NjdjYjYyODRlNSJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        4,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2FjODQ0OTVmYWU1YWI2YjMwMmQ2NjQ3ODMxZGY2NmNhODc2ZDk5ZjhjNTI4MDE0M2Q1YzZhZWJmNzhjOWIwIn19fQ=="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        5,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQzYmI0MGFlM2Q4YTU0NmYxYzM2YTVhMzZhZmMxMGQ1YTM0YWNmZDNjN2ZlMjY4MTljZWM2ZGY4NDgxMjdhYSJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        6,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI2YWQ4NGY5MjVhMWUwNTk0Y2E2YTBlMDI4NzU1YjliN2IzZTczMGIzZTAwNDVmODBjMGNmOTIzMDA2OGMzMiJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        7,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc5YTVmNWE4OTE5NGU4MWFmMzQyZTRmYTZjODEyZWQyNWUwOWM0ZmYzMmZmNDk2ZDA3ODM0ZDFjMDY3ZGZlOCJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        8,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQ0YWQ0M2U0NGJlZmVlYWYzNTQzYTVhOGI3NzQ5ODc0M2JiNDQzM2I1YjI0ZDE4ZjVkNWVjYzM4NTJmOTM3NiJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        9,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTcxZWNkMTJiYTgxZGE0MzE1N2YwOGU2NTEyZjBmOTNlOWM1N2IzMmExZjVhYTlhODRjMDE0YzUzODM0ODJmYSJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        10,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2YTY4YzMwOTU3OGM4YmI2MjVlYzM5MzFmOTdhNWFjNDJiNDEyMGNjZjFmZTQwYTczOTFlODhiMGI5ZTgxMSJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        11,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY0Zjg0MWQ1YWYyYWZkMWQ0NjFkMzRlOThjYzhiMzA2YWU3MDhlODcxZmI0YjQ2ZjE0YmIwNDQwZGM3N2Q3YSJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        12,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FhMGFiODMyMjQwOTZhYTgyMmEyNTVhZDI0MjVjODNiN2Q4Y2VmYjMzZGMyOGY2YWZjMjBmOGI2MzVmOTc2NyJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        13,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQzMDA3ZjU3NDAyYzJkNTc5YmYyOTRkMmJiNTI4MzgyMjk3Nzg4OTY0ODcyODg2YjdkMzFiZWI4YTFhOTE4NCJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        14,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDEzN2RiNTQ2YWI1OTkxNzA0ZWEwM2M1M2EwNTkyZThkNDM1MjFmMmM5OTczZmY3NmNiMGJkOWU5MTBjMjRhNCJ9fX0="
    ),
    EntityKey(
        EntityType.CAT,
        null,
        15,
        null,
        CatVariant.BRITISH_SHORTHAIR
    ) to EntityAttributes(
        "British Shorthair Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDEzN2RiNTQ2YWI1OTkxNzA0ZWEwM2M1M2EwNTkyZThkNDM1MjFmMmM5OTczZmY3NmNiMGJkOWU5MTBjMjRhNCJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNiYzBiZjNiY2IyYzUxZWQ0NDUzYjIwZTc0MWE4MGRjNWYwNjU1NTBiMWUzNTFjMGVhZmFkYzUwMjI1N2FmNSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY5ZjU1ZmI5ZGRiMTFlNDIzZmYzZTRhMGM2ZTA2MGUyMmM1NzdiM2NkY2EwM2M1NmM2ZDA2NzJkNTk5MjBiOCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWE0N2E5YTZmZDA4N2QzNDA1MTgyM2IxNTk2MmFmYTQzY2Y5Y2FkMTBkZDI0MzYyNzY2ODc0NGUyZDM0ODc1YyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJhNGJiM2M2OGJiYzY0ZjMyNzBiMTk2M2M3Y2VjMmNlZDhiOTJmZjgwZjAwNmM1ODA1MTdlMjJkMjRmYjcyIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzAyMmMxZjNjYjBlODkxYjVlMDNmNTA3ZDc5N2VlMGY5ZDg4Yzk1YTFkODBhYzY2ZDdjZDMwNjNhZDQ1NzE5OCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiOWFlNDc4MDM1OWIwNDg4YWY1NGQ2ZTg5OTI1NGExN2QzMTZlMDQ4MjE3N2I2NDg4OWU1ZTNiM2YwOTM2YyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2QxMTgxOTY3Nzc3Yzg2MTVkOGVjM2M3MzM2YWE3MmUzODczNGZhZWY1ZDg0NzVmNjVjZWRkMzJlMjdhMTRlYiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTdhNzQyYTA1NDg1ZDA1ZDEwNjlhMWZjNjk5MjE3MzgwYWM5ZjY0ZTY3OGY3YjJhZTIzMWQ5MmFjNWI4NTM5In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QzNDU5OGNiYzgyMDMxNzZmYjhkOWJhYjU3NDAwNjBmYTYwNzA4NjExMDMxZDMyMDBhZmEwNGUwOWZmNDkxMiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZhMmY0NWIzYjFiZjJjM2UxYzkyZDE0MjlhMmU2Njc4NTdiODQ1YmYyNjI3YWIwOTk4MTNjM2NhYzFhMjE2NSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI3MGE2NGJkMmRmZGI5NzdmNGFmYjVhYWEyNGM0YzNhY2M2MjFiN2YxMTZmNjNhNzdiM2QyNzkzN2RlZjc4ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZmNDkxNWUzZTU5NjczODFkNDNhOGI0N2YzYzNiNDk1MTBmYTJiOTllOWY3ZTY5ZGMzYjhmNWQwYmE2ZmE2ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNjJhNzEzMTdiMzZhMmUwOTk4MzUzZWI3NzYyMzE4MWY5MGM3N2JiYzFiNDY1MTEzY2M3YThiNDhmNzFlIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE1NjM3YjA4NWVjM2YzNjg3ODNiZmMxMDRmZDk3YTlmMzczMzhhYzY2MjRmNWQxNWIwOTExYzI3MTZjODA3MiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk5NjZmZGIwNWNmMWFjOTU2MjE5NDI2ZjM0MzgwMDJkMjk0ZWY3ODM2MTJiN2M0MGMyNTc2OGQwZTcwODQ3ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.CALICO) to EntityAttributes(
        "Calico Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZmNDkxNWUzZTU5NjczODFkNDNhOGI0N2YzYzNiNDk1MTBmYTJiOTllOWY3ZTY5ZGMzYjhmNWQwYmE2ZmE2ZiJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjlmMTY5OTJmM2E5MmMyZTYyZmQzOGQ1ZDg1MWZkNjYxY2ViM2U4YTgxMDIwMDUzNmVhNGIxMmViMDVmMzQxZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTc3N2YwZWM3YmJjMmZkMWIzYzkzMDY0NDM0ZWEyZDE0NzM0MDczMmIwZjdjYmYwMTg0OWI5ZmEyZjdkNjcwIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2M4YjhkZTFlY2RkYjllM2VmNmI4ZjY0MjhmYmVjM2JiZGNiZjUxODQyODY4M2NhNDgwODAzYzRmNTRmZjI5In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJhNGJiM2M2OGJiYzY0ZjMyNzBiMTk2M2M3Y2VjMmNlZDhiOTJmZjgwZjAwNmM1ODA1MTdlMjJkMjRmYjcyIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RkMTJlNmQ4NDVjOTM2YzEzYjUxMjcwYWExZTYzZDZiOWEwODBlOTljNTE3ZmI2OTFlNmExNjI2ZDc5NmQxMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJlNGE5OTcyM2M1ZTY3MzkxZGJiNzFmZTcwODNlMGYzNjU0MWM4ZTk3OWEyYTM1MTc4YzY3ZjQzYzAxMDUwNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzNTQ4ZDg5Y2U2MDI3NjJmZTcxNTU2Y2Y3ZDBlYTBkYmIyNDVmNjU4MzI2N2JhMDExZjgzM2IyYmQxODVlOCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwNmVjMWMyODQ1YzlmYjBmZDI3MDdmYzhlZTU4NTEyN2UwMjMzZWNlNTcwYjhhNzY0YjhlZjlmOTAyYWEyMiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjk1MjZmYTJlN2ZkNzEzZjllMDVkYTkzMWZhMjNkN2ZhMzUzNWE2YjBlMTY0YTE0MTc4OTkzOTE1ZTZkYTgwMiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjkwMzExNDFkNjdkMjllM2UwZWI3N2E2NWNiYzA3ZTI5NTU2NjZhYzY3ZTU0ODQwYjM5Y2VmOWRkMzY4NTA0YSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM2YzBjMWE1NDhjMGUzYjQ3MzI5Y2I4NzBjZWFhMTRiMWE5ZjM4MmJkYWRlYWUzZjhiNDFmNmQxMzY3OTg4ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc0MWIwNDg4MGM2ZjA0ZWQ1YTgzOTZjOTA5NjM0MjZlMTQzOWE0MmYyOGQwMmJkOTE2NjM0MDBkZmExN2ZhNCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyODg0MWQ3MWMzZmE1MWJhMWZhNmVhMGZlODU5YmQwOWIzNzNlZjg4MDg5YmU3NzE2ZTU4NmQ3MzM4MjFiOSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2EzM2ZhOGRiZWE1NWQ5OWNiMTQwZmE1NGJkZjBjNGNkZjU3MTAwZWRjYzNmNDk1ZDUyNzgzZmRhODI2MjZhYiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTRlMmRiNzJlYWRlOTgzNDNiZjQyYmE0ZDI5ZTBlMGI3ZDY1OWZkOTVkY2JmM2ZhMGQzZWUwMDQzNzNiMDdmMiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.JELLIE) to EntityAttributes(
        "Jellie Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmMyNDU3NmI2Y2UwOGIwNDliZmFmNmJmODM5N2ZjNWQzZDgzOGZmMzJiZTkzZGZkYmNiOGNhYTJiMzE2YjJiIn19fQ=="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZjZDJlMGMyYjVkZjk0ODJlZjQ1ZjVlMWQzY2YwNzc3OGZlYmRkODQ1NWQzZjAyZDMyNjAyOWFkNzlmNDFjZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGEyZTdiODdlNmFiMmUyYWY2MTRlMzA2M2NmYWU1YTc4NjE1N2IwNGE3OWQ1Y2I2M2JmY2NmOGRhMjZjNDhjNyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ3OTgxODk3YmQwMzkxMzQ2M2ZlNGJlNDhiMDJjYWFiNzg1MThjMmNjMTQ5ZmI2ZGNlZmI5MDZmOTljZGNjNyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZmU2NDU2Mzg4ZDk5OTNlMThhNGVhNDQzOGFhMGE5YWNiNDlhZWFjZTgzZDNlMmI1MjQyM2FmNGNkYTdjNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzJkNDU1Nzg0OWIzNTk2ZjYzY2VmZGZjOTBhOWZkOTE1MmVmMDQ1MWZiOGU3YTRmMmQxYWJkNmI4YjQwMjIwIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTYzMmYxZTY1NzhlYTE4Mzk1NzYwZTYzYmNkNzcxODhmZDFkNTBkZWI3ZDhiYjlhYTY1YzcxZTQyNjlkN2Y5ZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNjODVhNDI2ZmZmM2RjMDlkZmY5YzAwN2I4MDdiOGY5MDI0ZTgzOWIyZGMzYzllOWU4NGE5YjgxYTJhMWFkMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDA5YmEyYWZjYjRiODA4OTA1MDQ0ZTgyMjJlNWM1YzAwMDhhMTY4OTY4OTJlYzA5YmIwYTcyZTFmNmFiYjRjMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDFhNDM4ZmZjYWU1MzJlZDFlNWNiZjNlZTEyZTE1NDViY2NmZDJlMGI2OTZjMTA1MTY2NmVlYWViMWM1OWM3YyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhZmU2NDU2Mzg4ZDk5OTNlMThhNGVhNDQzOGFhMGE5YWNiNDlhZWFjZTgzZDNlMmI1MjQyM2FmNGNkYTdjNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQyZmMwNzJiNzBiOTIwZjE0Mjc0ZmQ2NWM4NjU5ZTM1MmJkMjJjMTQyNThkZWQ5OWM5NTU4YWI4ZGJjMzUxMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E0NmE3OTNlMTk2ZWNlNjVkMjFmNWNlMTUzZjVmMjE0ZDU5YzRiYTlhOGQzMzg2ZDQzNTU3ZTUxNWFhMzYzNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkwYzM4OWQ5MzU2ZDIzOGQ3ZTY1ZTMwNTkzMmE0NWU2YWYwMjFlZTdhNDVlM2I5YmQwY2QzMjdlMTQzYTg3OCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY2Mjg3ZjllNWNiNjU4NjZkMmQ0NjFlMDA4ZDY5YjEwNjFlZWI5MDZiNTFlZmI0YmQxNDVmMzJhNTM2ZTFiZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI2NTA2NjBjMDAzMGVmNmUwMGYzZTQyNmQzZmI0YWQwMzY1YWVhYjdmNWM1OThhNzQ0NmZkMDg1OTcwZTc0NyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.PERSIAN) to EntityAttributes(
        "Persian Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2MyZjk4NjU5MmViOTIzOTRlZjAyNzM3MmJmMDNhMGVjMzk3MTc3YWU0MmI0N2Y1ZTY5OTM5ZGQ5ODQ0NmQ1OSJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmU1NTIyYzNmMzBlMzY3NDYwNDFmODU0ZWExZmU2NjNiYzkyNjc1MTk0N2FiMWFkNTY1ODFhOGJjNjU3MjI5OSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwM2M3M2NiNmEwYTc4YjI0ZmViMWRlZDU5ODQxNWI4Y2I2YWE5MjQ1OGJmZDc1NzU0NGIzMmY5ZThmNzk2NiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY2OWUxMWU2OTI5M2ZlZmNhZjIzNDJmYTI3OWJjZTIxM2ZlNGMxOTEwMWRiOTRhMTM2NGM4N2VkMzRjMGY0OCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM5M2FmZmExZTRiZDAyMmUyNmI2ZGNjZGM3OWVhZTkyMDk5NDk5YTllYjkxYjU3MjA0MGQ2OWNiZDAyYzdmMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjI4YThiYmI4M2E1ZmQzOWE3Y2FjYjY2OGYyNGJmZjc0N2JjNDBhNWE4ZjMwMDg1Nzc1Y2Y5M2IyMDljMDQ1MCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdjMzYwNDI4M2VlYjdiYmUyOTZhM2UzN2ZhZTNiNmJiMzAwNjM3ZGNmMTdiMzgwNjMzM2RmYmMyZDQ0ZjIyOSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2VlMDA3NjIzYTFkODU5MjY4Y2JlYjkzMTY5NjUzYjdmZmYyMzVhNDZmZTNiMzAyYWVkMTU3MTVjZDUwNGRhZCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MzOTY4M2MzMmYzYWIyYTBmNGFiOTRiMTU5ZGNkNDhhMGIyNzEwY2M2ZGYwMWJhZDNmZDM0ZWI4ZWYzZGY5In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE3M2E1OGQxZDIyMzQ1YjFiZDQxMGU5YTYyN2U4OWU0ZjBiMzg3MWVhOWNhMDI3YjEyNGRhZDg0ZmYyZGIzMyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjM3NWU0YzFmODQ2MGZhMjNkZTcxZjJkNDlkMmZlNTJjOTJlYzMxZWZlOGYyNjRhYzRhZWJjMzk3NTdlYmI2In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWYwNmNkMTkxNGFiYjgyYjUyZjQyNzA5YjBjMjVlMGFmZmI5MmZjNGI3YmUxZWNmZWY1OWZiYmU4NjJhM2I4YiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNmZjFkYzdjZjIwM2U5NzdlN2JhMjg3NThiNWVhZmQ1MzBhNjJjOTgzZTdhNDFhNzFhODVkNmZlYjAzOTYyZCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU5MWU4Zjc4ODBjNGZjZTllZjVjOWFhMzgzNmU2YjAzMDAxNGRmN2ZkMjc1YzQwMzgzY2RhNmM3YjJmZjdiMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWEwYTUyODU0ZWFlZmQ3OTY2MGRkODRiNzg1ZjBkYTlmNmI1M2QzZTU5MWFmODI4MTEzNzEzOTljNjBiMzk0MSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU3MzBkZmNhMmQ1MWRlYjQ4ZDE5M2Y5NjA3NmI0OTNjOGJlZGQzZjg5NzJlMTcwODRkNmI1ODg5NTFhYTA4MyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.RAGDOLL) to EntityAttributes(
        "Ragdoll Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAyOGUzYzZmNDM1MGIzZTQyNGIzNzJmM2M5NDZmNTdiNzgzYjlhYTBlNGUyOTg5MWZmNTc4NDQxZWYwMTMwOCJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2MmI0YTQ1YzVmYTIzM2E0NjM5YWY2ZmI5MTBhZmMxNzk1ZWIwMDExZTQ2MjY2YTA4NjVkMTczZWJkZTlhYiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ4NjA3Nzg5ZWRlYTdhY2E3YTQwOGRjYTM0NDQwNzNkZTlhMGZlMTE3ZjEyNzI2M2Y3NzI1MmQwNzgzZTY0In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI2MmZjODJhN2FmZmQ3NGI2NDJmOTMyY2FhNzE4M2FhNjhhOGFjMTU5NzkyNzdjZTFlNmNhYjUwYzNlODk4YiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FlNWQ1MjkwNTU2MjA3NGRiMDA1NjhmYzE5NWNlNmIxMzc4MWY4ZTFjZWE0N2RkZDUxZmFiMTA2ZjFmYzkyMiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI0NDIzMmVhNTNhODJjYTgyOWI0YjhmYmI1NGEwNzVhMDc3NWMwMzlkMTIwOGFjM2NkYjM4ZjNkNGQwMTM1In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVmYzE3ZDRlNTc1OTUxNWE5N2VjM2EyOTZmOTg5ODZlMmUyOTAyYTQzMGE3YTAyYjQ0NTJmYTM2N2IwNmIwYyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI0YjFkZTkxOWQzMGUxMjM4MTgwMTFjN2U2NDZiNjU2YWZkMjYxNWZkOTBiZTNhMjY5Yjg4ZDQ1ZWYwYmQ5MyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2RiNzgyNWQ1ODIzM2Y5NTNhYTc0Zjc5MjQ0MTY5YjUxYTk2Y2EwMjAwMWMzOTRmNzM0MzVhZDFjMTQ3OGUxYiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y1NjhkZjUzZGM1YWQxZDk5MTFkY2U5YjIzNDRkYTFkZjFkNDdkZDhjYjRiZDg1NDAwZmVlYjhjZTE0OGU5ZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQzYjMxMjdhMGEwZGYzNTMwMzUyYzNkNTc1M2JhODQ1ODY3NGM5MDJjNTVjMjZmMzAwZDQ4YTY5OGE3Y2QyNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZjZDdjZDc1MDgyNTVkMGRhOTUxOTBiZDVhZDE2YzY1NDE3OTE1OTdhOTcwMmFkODc2OWZkZTljOTdiNDQ2MiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVhZWQ3M2UwODExN2NhMDU0ZjM5MjIyNjI0ZmJjOWZiNmY2YzAyY2QxMGJkMmQyMTlmZWIyN2QxNzEyNmIwZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzMzNTM0YjBiMzI0MzRhN2EwZDA3NmJhZmI3YzliMDkxYTQwMGExYzU2N2ExZmMwOGM3ZDIwMDUwMzkxNGMifX19"
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWJjZjUxM2Y1MjU3NjkzZjAxNWY4NzhiZjIzZWRiOTg5YWIyODQzZDYyZDVmZDdiODlmMTY5NzAwYWQyMTExYSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFhNDdhNDY2MzQ3OTYwNjRhY2IyNWUyNDgyMzE0NzBlYTU4MDYyMmRhNjVmMThhZjJjMWQzNzI3YzE0OWEwYSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.RED) to EntityAttributes(
        "Red Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTdkYjM2OTRiODgwNGM4OTgyMjJiMWIxMjg3OWU3OWY2MTY1NTAyY2YyYTgyNTM5NDJkMjNhYWQzNjJkZDRjZSJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE5Nzk0NjE2NTAyN2Q2ZThjMjEzNTM0NDNlM2IxMDNlZWY0YmVlMWExYzIzODk4NGZlMGFmYjA2Njg1OWQ0NSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRkZDRlZTlhNWQ3YTRjZDM2ZTQ5YmY3ZWExNjIwNjg4MmFlMjg1YTQzMzZkNDBiNjEzZDkzZGI4NzNiNDQ2YyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2JhYWQ5YTllOTk0OWQ1MGYzYWYxNjIwMzBlNzM2Njg0N2FiZTQzZDI1ZjEzNzI4MTkzMDFiMWZhNDI5ODA5MyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIwNDI3MGQ3ZGJiNTMzYmMzYTZkZDI5YjU5M2M1ODRiOGM2YWRmZWNlMTk3MzVkM2VhMWZlZWMxM2JjYmU2YSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIyZDExYjFlNjVhZGFjNWJjNmNiNWU3Y2IwODQ1ZThjMzExMTU2NDBhYzc0NjZkOTgzYjlhYmQ1YTE1YjljMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UzY2I3NTMxOGZhZDc4NWI0ZjUzY2QzOWFjNWUyNTk1NmZlMjc0YmMzNTRjZTcwMTZkNmU3NDA4MzMxZGE3NiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRmOTg0MWZjY2NkZjA5YzUwYWJhOGIyY2Q3NGNlNzJhZWZlNzgzY2FmNjkwMjExZDFiMWQ1MmJkODVkOTJjNCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGExOTVlMWY4ZjQ0ZDMyMWJhOTJjMjQzNWQ5YjdhYzc4MjYwYjMzM2M0ZDg0ZGNhY2Q5ZDU0MGM2ZWJmNDJiZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjVkNTVlYzU2OWYxNzM4NmYwODk4YzBhMDY4MDhlNDI1OGMxODVlNGY3MjQyZjdiYWI5MTIwYWM0YjViZTgyZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWVkYmNkZjE1MjYxNmVmZDlmZmZhMTg4ZmZmNmNjODM5YTZhZWVjODljMjM0ODgxYTE3NjU0MzYxY2ZlMDk0MSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJlN2Q5NTkwODFkYWM3ZWNmM2Q2YzU3MzhhOTNjOGUzMTIxYWYwNGUwNDBjODRlZWJhYjg4YzA0NDAxOTllMyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ViZmI3ZjM5MzNlYWUxMTY4NzdiMTQ0NGQxZjc2OWJmZjNiMDcwOWZlNWViODAyM2ExOTNjYjFlODY0ZmI4MSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU0NDczNWU4NDdlNzEwNTE0OThiZmQ5NWZiOTkyZmE0NDk3YTUwZGUxZDRlM2I1OTkwM2IyZTU2MWY4YjQwYyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU3Y2I2YzdiN2UzMTNkNWE1YTI2N2EzYmQwMjM4ZWYwZWYzYTkzZjIyZWEwZDg3N2JmMWM4MGI3MzFlNGZkMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdkYTdjMTgxYzFjYmFmODkxNTU1NzA4ZGY1OTI5ZDYzZTgyOWVjMGZlMDEzMjI3MTU2MjY3Y2RjODA0NDkzNSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.SIAMESE) to EntityAttributes(
        "Siamese Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU0NDczNWU4NDdlNzEwNTE0OThiZmQ5NWZiOTkyZmE0NDk3YTUwZGUxZDRlM2I1OTkwM2IyZTU2MWY4YjQwYyJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRhOGE3YmY1ZTc1MGY4MDhlNGZiZDQwODk0ZGZlNzQ2YTA3MjQ1ZGMxNmExMTM3NDNhZTM1ZmViNWIwZjc3MSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjYyYzA3NjJkYWZmNWNjYjc3MWI4ODBlMDhmNjU2NDMyOTgzNWJkNmE2NGU0NWRjOTI4MzllYjE3NjkxOTU2MyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRlYmRiNDdlOTdiODc3ZjQ2MGM0YWUzMGY3ODhjMGRmNzk0ZWU4YmFkMzU1YTdjNjMyZjg2MmU5NTJiY2Y3MiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjEzODkyMzZkZjUwNDNhMDA1YWFjZWI3YTJmMmU5ZDQxOTIyYmQ4NmJkZmNkYjU1NmE4NjFkNmExOTY5NzMxMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RjYTlhNjUxYTVlOWYzNjRiNGFlZWFkMmVkZTcxM2NkMzIyNjI3YmM4MjkyNjc3YjBiZjQxZGRiNWYxNzUzMSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZhOGIxZDRiODlmMTA0NDkyZjg2MDRlNDgzZWQ1Y2M1NWE5Y2U0NTEzMzM1Y2EyNTJjZjNlYWZlNzE0MzRkNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE0MDI5Mjk1MWU1OGVhZTMxY2E4YTU5OTU1MGU3YjVjNjY0Zjg3MTk1NzM2NDQ3ZjEyZTQ2YjQxN2FmZjNhNSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWZlOGQ3ZTFhNmZmMWJmNzlkNmJkNzIxNTkyOGI0M2UwZWMzODJjYjZjOTNkZjdmMGRhZmY5NWFhNjAzNGVkMyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDU1OWM4MjMzZWE4Mjg3OTI0OGNkN2RkNWRiZmIxYTExYjk5ZmY2ZDI0ZGI1MjEzOWE3ZDQ4NmQzNWYxNzAwNyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNiZWE2ZWEyYmMwMDc1NGU1ZWJhNTIxYTQ1MjdiYzNmMjI5NzczZDQyYjM5Y2Y5OTIyYTBhZDc0ZjliODc4NCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhjYzQzY2Y0M2VlYTk2YjhmN2NlOTUzZGM0ZTI0NGM5MzQ4MGFjZjEzNDRmMGY2ZmJkYTY0ODUwNGFkMGUwNiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZhMzEwNzU3MDMyNjFkMjRmODE4Y2M0OTcxM2ViMzcxY2VlYWY5ZWE2ZTA3NTg4ZTg5MzMxZTI2ODQwNTM3YyJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjRmZmFjOTMwM2Q3MGY1YTI0NTgwYjBlZTFkOTNjY2E4ODMzZDdkMDk3NmI1Mjg5NjhkZTJlOGE4OTI2YjhmMCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQyMTk4OWM1ZmJkMzZlY2JlMTIwZmRmNTE4NGJiNmRjNWNiNWE0ZTQ3YjM2MzhmNTZiODhiZDQyMjU0Zjg0YSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODlhZTQyNGY3YjJkZjk2YzFkYjZjNzExZTUyYmVmYjQ3OTgwMjNkMWIyMmQ5MmQ4MzIwMmZlNDA4NzUwMDQxYSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.TABBY) to EntityAttributes(
        "Tabby Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhlMDI0NjkwNDY5ZjM0ZmJiZTRiM2E0OTQwNjhjZjA5MzE2ZjAyNzY1ZmFkZTE2NGY0ODg1YTBhZWQ2MDg1NSJ9fX0="
    ),

    EntityKey(EntityType.CAT, null, 0, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjdmNTk2YWZiODY5ZjgwNmY2MTA4NWE0OTliN2U3NTE0ODkxM2QzZTYwNjAxZjE2NjI1OGRmYmNiODJhM2JiZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 1, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg4Mzk0Yjg0NDQzZmJjMmE2MDFjNDQ0NjMyODQ4ZDQxNGFmYTY5MzA4N2U3Y2IwMGIzN2FiNjMxMjk0MDkzOCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 2, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWIzYTZmYzI5MTNlMDdhMDY0YTlmNmJlOGFiNzA4ZWUwNmMwYTM1MjBiNjljZDBlYjZjNjgxNTRmNWM0YWJmZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 3, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRhYjc3NjIzYjY3OTU2N2UyYTAwZTRjYTExMmZlYzFmYWVkM2ZiZjU2MDU3OGY0NzQ0ZjgwMzc3YThmMjMyOCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 4, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzE2MjliZDk2YjkyNWI1N2M1ZDk2NmEyYmU4NjJjMTUzY2E2M2YwOTk1MjQ2ZjkzMmE1MDZmY2QzODdhODk5NiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 5, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg0Yjc5N2QzMjQ3NmE1M2Y5OTkzOGI4NzQ1NGRhYTEyMWJiNTRiN2U2NzZjYWY2OTJjMDdmNGYzYjE2ZTQ1ZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 6, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjY0ZTBmODIxNzcxMDdkMGM0NDY5NmMxZjAzZDRkMzQxNDgyNDNlYmQ5ZTBjMmViYzNiNDk2ZjFiZDNiMjY4In19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 7, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY1NDBmMTJiY2VkZGYyNmJjZWE0ODIzOWQxYTBjZjkzZDA0YmY5YzlkYjM3YTk4MzQxODRhZjdkYWE0ODQzZCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 8, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwYmY5YjgwM2EwZDJiNzY1NzI3MTc0NDJiMDVjODNhNWU4ODdlZWI2MjAzMWIyMzdhNzdjNDJmYmYxN2IxZiJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 9, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTFmYmMwZDNjNTk5OGVlMmMwODBmYWFiNWRhMGIxNzBlMDM4MWM3Yzk1MDEzMDM2OGQ3ZjJjYzNlZDgwOTk2ZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 10, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MwMzAyYWJlN2FhZWNiNWIwNTA2NDA2MGJiOTRhNzM5ODJhOGZiM2E2MzJiYjZlYWNiMzAzYzRlNzRmODhhIn19fQ=="
    ),
    EntityKey(EntityType.CAT, null, 11, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ0ZTk0YjFiZGIwMjU3ZmY4ZTgyZDY3ODgzY2YxMzJmZjg3ODUyOTY4ODM3NjAzNzAzN2Q1MTE0ZDM0NWY1ZCJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 12, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q2ZGNmNjIwZjk4NzlhYmU5Y2QwOWE3OGZiODY0ZDA4NWQ3MWIxMmJlZmY3NjkxNWYxMGZhNDljOWNjMzEyZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 13, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzU3OTJhMjkzNDUyODUxMTA1MWYyOTI1YzI3NTVkOWQzYjM2ZjdmYmM4YTQ0ODI4MjhmZDZhODdiM2NkMWQzYSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 14, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg2ZGZmNmZkM2IwZDAwNjQzYmQ1ZjI0ZTUyZjk1ZGRkNGM3YjEzN2QwMjQyNjI2NTY2NWNjNjU0MTY3ZDg4ZSJ9fX0="
    ),
    EntityKey(EntityType.CAT, null, 15, null, CatVariant.WHITE) to EntityAttributes(
        "White Cat - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY3M2I3NmI5NTQ2MTE5Y2RiYmM1YjhkMzdhZGVmYzE4ODU0YTYyMDk5ODFkYjE0ZWMwZWYwZGNkNmYxOTQ2OCJ9fX0="
    ),

    //Axolotl
    EntityKey(EntityType.AXOLOTL, 0) to EntityAttributes(
        "Lucy Axolotl",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I5MTBmYmMyMTZmNzI0ZDI5NjU1MTU1YjJhMzg1OGE4MGYyMzRhMGNmZWQ2MDllMjJmYzY3MDY4M2FiNzc3YSJ9fX0="
    ),
    EntityKey(EntityType.AXOLOTL, 1) to EntityAttributes(
        "Wild Axolotl",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWExMGJjNGYzNjhlMTJjY2JkZWE0NjA2ZDJkM2ZiMjE5ZTkyNjM0MTA1ZDE5NzRkZTcxMGQxZmRiOWIwMjlhYyJ9fX0="
    ),
    EntityKey(EntityType.AXOLOTL, 2) to EntityAttributes(
        "Gold Axolotl",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTE3ZjhlNThhZGY4ZTFjNWVkZTQ1MGQyMjM1Y2ZmNjEyZmMxNmM0Yjc0NDkwYTBlN2RiZDU5MzBiOGM4M2U3ZiJ9fX0="
    ),
    EntityKey(EntityType.AXOLOTL, 3) to EntityAttributes(
        "Cyan Axolotl",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU4YzcwZTZhNTYxNjFlY2RjNmI3NTY3NDA3MGZiMzA4NzE0YTJiMmI3MGUxZTZkNzZiYzkyMGNlNmNlN2RlNiJ9fX0="
    ),
    EntityKey(EntityType.AXOLOTL, 4) to EntityAttributes(
        "Blue Axolotl",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjgyNjg3YzBlMzJmODBmYmYyNzUyZjJhNzVjM2JiZTBjZTk1MjU3MDRiNjVhNmQyNDFmNGM0NGRlMzk2MmE3MyJ9fX0="
    ),

    //Fox
    EntityKey(EntityType.FOX, 0) to EntityAttributes(
        "Red Fox",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y4OGJkYThhYTBmOTQzNDQ5Mjk3MTBiZDdlN2Q2NjhhZGUzMjBiYmRmYjM1MjZkMGMxOWE2ZWY1MTk3MDJjOSJ9fX0="
    ),
    EntityKey(EntityType.FOX, 1) to EntityAttributes(
        "Snow Fox",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRkNjQ1NTlmNjhmYmNlMzM2NjI0ZTEzNDI5NTBhZWE5ZjEzNTQ5NGMzZWMxNjIwOGIzZDNjY2Y5NGNmMzBlNCJ9fX0="
    ),

    //Panda
    EntityKey(EntityType.PANDA, 0) to EntityAttributes(
        "Normal Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE2ZTNhZDgyM2Y5NmQ0YTgwYTE0NTU2ZDhjOWM3NjMyMTYzYmJkMmE4NzZjMDExOGI0NTg5MjVkODdhNTUxMyJ9fX0="
    ),
    EntityKey(EntityType.PANDA, 1) to EntityAttributes(
        "Lazy Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTYyYTAyNGU4NzFiZmIyZWI5OTVkYWQyMWU5ZTcwNDg5MDQzZDNjYmM3M2Q3ZmE1NTIwYWViNzY1OTkzMzQ3In19fQ=="
    ),
    EntityKey(EntityType.PANDA, 2) to EntityAttributes(
        "Worried Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI2NjY3MmU0YmE1NGM1YzBiZTU5ZTA0NjFhNmUzMmVhMGE3Y2YxMTVkNzExODY3ZDI5MjJlZTljYTUyMzY5MCJ9fX0="
    ),
    EntityKey(EntityType.PANDA, 3) to EntityAttributes(
        "Playful Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZjMTUyNzI0NmZkYTNlODMxMTI1MzQ0MTRiZGJjYzNjMTBmM2JhNWNmMDhlNDdhZjQ4YTE4ZTc2ZWUxNDhmZCJ9fX0="
    ),
    EntityKey(EntityType.PANDA, 4) to EntityAttributes(
        "Brown Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjRmN2M3M2ZkYTZhMzRjZjhiZTRjNzkwN2RkMGY1ZjA4NjVkZDc3ZmQ4ODJmYzYzMzU2MzY0OWM1NzUxN2NhZSJ9fX0="
    ),
    EntityKey(EntityType.PANDA, 5) to EntityAttributes(
        "Weak Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY3MjE5YTQ2ZDM5NTdlMzI5MmI4MWQyZTI4Zjg2YzYzNTAxZWNiNjY3M2FjZTMyNjAzNWI1MjI5YmQ4ZGI0YSJ9fX0="
    ),
    EntityKey(EntityType.PANDA, 6) to EntityAttributes(
        "Aggressive Panda",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTg4MGUyMzY0OTRlNzEzNWRiOGVjNDVmNjRiYTkyNDljYWRiOTRhNWMxYTdhNTE1N2YzZTAyYjAxYmZkYjBmNiJ9fX0="
    ),

    //Villager
    EntityKey(EntityType.VILLAGER, null, null, "none") to EntityAttributes(
        "Villager",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhOGVmMjQ1OGEyYjEwMjYwYjg3NTY1NThmNzY3OWJjYjdlZjY5MWQ0MWY1MzRlZmVhMmJhNzUxMDczMTVjYyJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "armorer") to EntityAttributes(
        "Armorer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjYwNWE1MGRkNTllNTIyNzcwMWE1MTk3MGFiMDQ3OWVmNTdmZGFkOTA5OTA3MzUzOGZhNzEzYjI2Y2NlOWZjMyJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "butcher") to EntityAttributes(
        "Butcher",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY3NzRkMmRmNTE1ZWNlYWU5ZWVkMjkxYzFiNDBmOTRhZGY3MWRmMGFiODFjNzE5MTQwMmUxYTQ1YjNhMjA4NyJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "cartographer") to EntityAttributes(
        "Cartographer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQyNDhkZDA2ODAzMDVhZDczYjIxNGU4YzZiMDAwOTRlMjdhNGRkZDgwMzQ2NzY5MjFmOTA1MTMwYjg1OGJkYiJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "cleric") to EntityAttributes(
        "Cleric",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg4NTZlYWFmYWQ5NmQ3NmZhM2I1ZWRkMGUzYjVmNDVlZTQ5YTMwNjczMDZhZDk0ZGY5YWIzYmQ1YjJkMTQyZCJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "farmer") to EntityAttributes(
        "Farmer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVhMGIwN2UzNmVhZmRlY2YwNTljOGNiMTM0YTdiZjBhMTY3ZjkwMDk2NmYxMDk5MjUyZDkwMzI3NjQ2MWNjZSJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "fisherman") to EntityAttributes(
        "Fisherman",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM3ZjQ0YjUxMWY3MDYzMTg3ZDBmZTEyYzc0YTBjYjhjOTNmMzRkMGY1ODczMzhiMmE5YzIyZjNmYTJmMjEyIn19fQ=="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "fletcher") to EntityAttributes(
        "Fletcher",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc1MzJlOTBjNTczYTM5NGM3ODAyYWE0MTU4MzA1ODAyYjU5ZTY3ZjJhMmI3ZTNmZDAzNjNhYTZlYTQyYjg0MSJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "leatherworker") to EntityAttributes(
        "Leatherworker",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQ1Yzk5YzgwZDAzNDVjNGJlM2ZjM2EyZjBkMDVhM2UyM2E1YzRiZjdlOTkxNTY4ZWVlNjRhNjgwNmYwNDhjMCJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "librarian") to EntityAttributes(
        "Librarian",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTgwNmY5NzY3ZjA4N2UzZTRjMDlhZDAxMmJmZDA2M2QwMTNiYTRjMzE2OWZiYjBlZmQ3NTM4ZTI4ZDdkODNkIn19fQ=="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "mason") to EntityAttributes(
        "Mason",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxZWM2MTJmOGY3ODk4NGEwOGY4MjkwYmQzZjFjMTg5MmI0Zjc4MjdiNTI0ZGJhYjdlYWFjYzlkZDllMjJiMiJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "nitwit") to EntityAttributes(
        "Nitwit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzVlNzk5ZGJmYWY5ODI4N2RmYmFmY2U5NzA2MTJjOGYwNzUxNjg5NzdhYWNjMzA5ODlkMzRhNGE1ZmNkZjQyOSJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "shepherd") to EntityAttributes(
        "Shepherd",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU5NTA5OWExNTk0MGRhZTc5NTM5Nzg5MjYzYTViNjliMGU0MDk3MzA1NTI0NDUxNzExMGFjZmIwZjRlOTQ0YyJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "toolsmith") to EntityAttributes(
        "Toolsmith",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RmYTA3ZmQxMjQ0ZWI4OTQ1ZjRlZGVkZDAwNDI2NzUwYjc3ZWY1ZGZiYWYwM2VkNzc1NjMzNDU5ZWNlNDE1YSJ9fX0="
    ),
    EntityKey(EntityType.VILLAGER, null, null, "weaponsmith") to EntityAttributes(
        "Weaponsmith",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU0MDliOTU4YmM0ZmUwNDVlOTVkMzI1ZTZlOTdhNTMzMTM3ZTMzZmVjNzA0MmFjMDI3YjMwYmI2OTNhOWQ0MiJ9fX0="
    ),

    //Parrot
    EntityKey(EntityType.PARROT, 0) to EntityAttributes(
        "Red Parrot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTRiYThkNjZmZWNiMTk5MmU5NGI4Njg3ZDZhYjRhNTMyMGFiNzU5NGFjMTk0YTI2MTVlZDRkZjgxOGVkYmMzIn19fQ=="
    ),
    EntityKey(EntityType.PARROT, 1) to EntityAttributes(
        "Blue Parrot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFjNjcwM2RlZDQ2ZDkzOWE2MjBmZTIyYzQzZTE4Njc0ZTEzZDIzYzk3NDRiZTAzNmIzNDgzYzFkMWNkZCJ9fX0="
    ),
    EntityKey(EntityType.PARROT, 2) to EntityAttributes(
        "Green Parrot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTcwZWU0ZGY1ZWI3NDJkYzhiNjIxNjM3Mzg0ZTQ4OTAzNjNhM2U1Nzg1ODM4NDYwNmE1ODg4YmVkNGRhMzkwZiJ9fX0="
    ),
    EntityKey(EntityType.PARROT, 3) to EntityAttributes(
        "Cyan Parrot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM2NDcxZjIzNTQ3YjJkYmRmNjAzNDdlYTEyOGY4ZWIyYmFhNmE3OWIwNDAxNzI0ZjIzYmQ0ZTI1NjRhMmI2MSJ9fX0="
    ),
    EntityKey(EntityType.PARROT, 4) to EntityAttributes(
        "Gray Parrot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTNjMzQ3MjJhYzY0NDk2YzliODRkMGM1NDAxOWRhYWU2MTg1ZDYwOTQ5OTAxMzNhZDY4MTBlZWEzZDI0MDY3YSJ9fX0="
    ),

    //Trader Llama
    EntityKey(EntityType.TRADER_LLAMA, 0) to EntityAttributes(
        "Creamy Trader Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg5YTJlYjE3NzA1ZmU3MTU0YWIwNDFlNWM3NmEwOGQ0MTU0NmEzMWJhMjBlYTMwNjBlM2VjOGVkYzEwNDEyYyJ9fX0="
    ),
    EntityKey(EntityType.TRADER_LLAMA, 1) to EntityAttributes(
        "White Trader Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTVhZDZiNjljYzZiNDc2OWQzNTE2YTBjZTk4Yjk5YjJhNWQ0MDZmZWE0OTEyZGVjNTcwZWE0YTRmMmJjYzBmZiJ9fX0="
    ),
    EntityKey(EntityType.TRADER_LLAMA, 2) to EntityAttributes(
        "Brown Trader Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE0ZWVkODU2OTdjNzhmNDYyYzRlYjU2NTNiMDViNzY1NzZjMTE3OGY3MDRmM2M1Njc2ZjUwNWQ4ZjM5ODNiNCJ9fX0="
    ),
    EntityKey(EntityType.TRADER_LLAMA, 3) to EntityAttributes(
        "Gray Trader Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBmZGZhNjBjNjI0ZmI2NjdjODMxM2IyZmIxZGFiNDBlMGFkMmU2ZTQ2OWI1NjdiZjU5NmFkMjYzOTIzMTljNSJ9fX0="
    ),

    //Llama
    EntityKey(EntityType.LLAMA, 0) to EntityAttributes(
        "Creamy Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFlMjVkZGMyZDI1MzljNTY1ZGZmMmFhNTAwNjAzM2YxNGNjMDYzNzlmZTI4YjA3MzFjN2JkYzY1YmEwZTAxNiJ9fX0="
    ),
    EntityKey(EntityType.LLAMA, 1) to EntityAttributes(
        "White Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU3MDNhYjAzMWVkNjY2MjJmMTI5NTdlZjU5YThiNWM4YTI2OWNlYmQxOGY5MzI2MjQ4YjY4YzNiYmUyMDE2MyJ9fX0="
    ),
    EntityKey(EntityType.LLAMA, 2) to EntityAttributes(
        "Brown Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y4MzI0NjZkY2M3ZDVlNzcwMmNkZWU0Y2Q1NTVkYmQzOTYzN2QyMGFkZjkzNjdmYjAzY2ZkNjg4OGJhYWFlNyJ9fX0="
    ),
    EntityKey(EntityType.LLAMA, 3) to EntityAttributes(
        "Gray Llama",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQyZmZjZTlhMTc0ZmUxYzA4NGUyZDgyMDUyMTgyZDk0Zjk1ZWQ0MzZiNzVmZjdlYTdhNGU5NGQ5NGM3MmQ4YSJ9fX0="
    ),

    //Rabbit
    EntityKey(EntityType.RABBIT, 0) to EntityAttributes(
        "Brown Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzFkYjM4ZWYzYzFhMWQ1OWY3NzlhMGNkOWY5ZTYxNmRlMGNjOWFjYzc3MzRiOGZhY2MzNmZjNGVhNDBkMDIzNSJ9fX0="
    ),
    EntityKey(EntityType.RABBIT, 1) to EntityAttributes(
        "White Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBkY2RkYzIzNjk3MmVkY2Q0OGU4MjViNmIwMDU0YjdiNmUxYTc4MWU2ZjEyYWUwNGMxNGEwNzgyN2NhOGRjYyJ9fX0="
    ),
    EntityKey(EntityType.RABBIT, 2) to EntityAttributes(
        "Black Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlhNjc1ZWRiM2NiYTBmMzQzNmFlOTQ3M2NmMDM1OTJiN2E0OWQzODgxMzU3OTA4NGQ2MzdlNzY1OTk5OWI4ZSJ9fX0="
    ),
    EntityKey(EntityType.RABBIT, 3) to EntityAttributes(
        "White Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJmMzllMGE2MDMzODZjYTFlZTM2MjM2ZTBiNDkwYTE1NDdlNmUyYTg5OTExNjc0NTA5MDM3ZmI2ZjcxMTgxMCJ9fX0="
    ),
    EntityKey(EntityType.RABBIT, 4) to EntityAttributes(
        "Gold Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE3YmRjYzljYWQ1YjJhYzJkYjQwYzU3NjEzNDljMzNlNDBhYjEwOGRkYThjZmE3ODhlOGNmZWExMTNkNGE3YyJ9fX0="
    ),
    EntityKey(EntityType.RABBIT, 5) to EntityAttributes(
        "Salt Rabbit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M0MzQ5ZmU5OTAyZGQ3NmMxMzYxZjhkNmExZjc5YmZmNmY0MzNmM2I3YjE4YTQ3MDU4ZjBhYTE2YjkwNTNmIn19fQ=="
    ),
    EntityKey(EntityType.RABBIT, 99) to EntityAttributes(
        "Killer Bunny",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWY5ODMzN2I4MjQyMjI5ZDk1ZGEyMzA5MDc1NTc4OTc3OGIxOGJmNWQwN2Q2MWY2MjBjZGJkYmJkMjlmYTYxNSJ9fX0="
    ),

    //Horse
    EntityKey(EntityType.HORSE, 0) to EntityAttributes(
        "White Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIwM2ViMTNkNzk4ZjM4MjM3MDMzNTdhYTZlOGZkMjlkZmYzMDEwODcxYjRlNTRmOTI4ZTI3MGQ3NDEwOTY5YiJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 1) to EntityAttributes(
        "Creamy Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZkYWUwYWRlMGUwZGFmYjZkYmM3Nzg2Y2U0MjQxMjQyYjZiNmRmNTI3YTBmN2FmMGE0MjE4NGM5M2ZkNjQ2YiJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 2) to EntityAttributes(
        "Chestnut Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk5NjM5OWZmZjljYmNmYjdiYTY3N2RkMGMyZDEwNDIyOWQxY2MyMzA3YTZmMDc1YTg4MmRhNDY5NGVmODBhZSJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 3) to EntityAttributes(
        "Brown Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVjZTE5NGE1NDMxNWFjYzNiZjlkYjdlZGY2ZTdkYTI5ZjQ5NTI0YjFiOGFmMGVmOWU0YWMzZGYyMjgwYjBkOCJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 4) to EntityAttributes(
        "Black Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU4OGQ0MjNjMTdjZjZhYWJkM2YxMjU0MjRlOTE4NWE5MzZmNDNlYjdiM2FjMDhjODEzMmQ0Y2IyYTBiYmI3MyJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 5) to EntityAttributes(
        "Gray Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYwZDk1NTg4OWIwMzc4ZDQ5MzNjOTU2Mzk4NTY3ZTc3MDEwM2FlOWVmZjBmNzAyZDBkNTNkNTJlN2Y2YTgzYiJ9fX0="
    ),
    EntityKey(EntityType.HORSE, 6) to EntityAttributes(
        "Darkbrown Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU2YjdiYzFhNDgzNmViNDI4ZWE4OTI1ZWNlYjVlMDFkZmJkMzBjN2RlZmY2Yzk0ODI2ODk4MjMyMDNjZmQyZiJ9fX0="
    ),

    //Snowman
    EntityKey(EntityType.SNOW_GOLEM, 0) to EntityAttributes(
        "Snowman with Pumpkin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ0N2ExMDFkZmQzY2E3YWIzNTJmZDUzMDJmOWUwNmFkYzBkZmVkYzg1NDRiN2YwMmI5OTFhMzhmZjA5NTBlNCJ9fX0="
    ),
    EntityKey(EntityType.SNOW_GOLEM, 1) to EntityAttributes(
        "Snowman without Pumpkin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTExMzY2MTZkOGM0YTg3YTU0Y2U3OGE5N2I1NTE2MTBjMmIyYzhmNmQ0MTBiYzM4Yjg1OGY5NzRiMTEzYjIwOCJ9fX0"
    ),

    //Wolf
    EntityKey(EntityType.WOLF, null, 0) to EntityAttributes(
        "Dog - White Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjVhMjlkNTk0NTFiYWYwNTc3NWEzOGZmYjhmYjQ2ODAxN2Y1NDEyNmUyMGYwZjhiY2IzZTJkMTEzNjkxOGFkMSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 1) to EntityAttributes(
        "Dog - Orange Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU3MjA2OTc1NGU2NmMwZDc4OGM0MDk1ZWViNDM2MTIyZTRkNjAwZTA5MWI3MThmNzQ0YTkzOGI0MDE3YmM4ZSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 2) to EntityAttributes(
        "Dog - Magenta Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE4ODE5ZmU4YWVhNTE5ODViMmY2MzMwOTk1YjE0MGE1NGJhNjk2NGU1NTkxNDYyNzA4ZGQ3YjA2Zjg4YjRiMyJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 3) to EntityAttributes(
        "Dog - Light Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTk5MmY4MmI3ZGE5OTIzYjIxODYxMGUwNGIzYzM0ZWMzN2I0OWQyMzExYWExOTlmN2IwNDFhMzBjOWU1YzZmYSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 4) to EntityAttributes(
        "Dog - Yellow Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZmOTVmNjEzZDFiMGI4NzU1MTZkN2FjMmIyMmY3YzVhM2JjNmU1NmQyMmE1NjQ1NTk2NDZhNjgzZGYyMjMyMiJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 5) to EntityAttributes(
        "Dog - Lime Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE5NjUwMmM3YmMxYmNkOTc0ZjZmMDNhYzU4YmExM2NmODliMjAxZGI3MTQ3MzYyYzk3ZDcxN2U0MTE3YTYzIn19fQ=="
    ),
    EntityKey(EntityType.WOLF, null, 6) to EntityAttributes(
        "Dog - Pink Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2YxOGQxZTQ5OWYwZmU5ODg2ODY0Zjc0Nzg1MmEwYjgxMDc5NWY4OGQ0YzVmZTg5MDI0ZjViZDg3MDcwNjliMSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 7) to EntityAttributes(
        "Dog - Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFkMTM3OTE4OWRjMTAxOTQ3M2Q4NTVlMGZiN2FhZGI2NDk0OGQyYzFlM2Y5YWVhZWQ0YzQ0NTRmMjAyYzhkYyJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 8) to EntityAttributes(
        "Dog - Light Gray Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBkMGE4MGJmNmU5NTE5ODgwOTZmMTkyMzI3MTRjZjA3NWFhMzZiNzMwYjk2ODU2ZWY0ODVlZThlZmVjZTE3MCJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 9) to EntityAttributes(
        "Dog - Cyan Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3ZDQ4ODRhYWYwNjA5MTNlNGZhNTlkMzQwYTA0ZjJjOWIwOWNhZTc1OTQ4NDZiNGQ5OWI0ZjY2MmIyMmQ4NSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 10) to EntityAttributes(
        "Dog - Purple Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkNmE3OWQyMDdiNzQ5ZGYyMzRjYzM1MGZmMWNkYjFjNzU1MGQyNzZmZmRhYTFmNTUwNzU2N2Y2ZGQwMzY5MyJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 11) to EntityAttributes(
        "Dog - Blue Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFiY2UzZTk2ZTRkYjgzOGRiOTc5YmRjYzdhZmExNjE5MDc3M2E2N2JmZmY5MTZjNzQ2MzMwOTA5NGU0YzBkMyJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 12) to EntityAttributes(
        "Dog - Brown Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkwODFjYmM0NmJiNDdjZGNjMzliZDQ1MTE1OGFkN2Y5MzY4OThjOTkzM2JmYTczYzNkMDU4MDE0MDUwZjViNyJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 13) to EntityAttributes(
        "Dog - Green Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGQxMzdjYWRkYzgxMWFlZTA4M2UyZWY5OTdjNzk4ZWI2NGJlZjBhNmZmYWU1ZmVmM2U4YjQzZDhmZDc2MGExIn19fQ=="
    ),
    EntityKey(EntityType.WOLF, null, 14) to EntityAttributes(
        "Dog - Red Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZhOGFlYzhmMzI5OTgxZDdhYWQ2N2E0MzcwNGMzN2QyMGVhNDcyNjNhNTNlYWU3YTI0MGM5Mzk2NWQwOTQ4MSJ9fX0="
    ),
    EntityKey(EntityType.WOLF, null, 15) to EntityAttributes(
        "Dog - Black Collar",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNkMmMxYzA0MTFlYTJiNzYzZjU5YmQ5YTAzZjljNDg3YzY4ZDQ1Yzc1ZGZmNjljZWVhZmZkOTc4Zjk3MjI2In19fQ=="
    ),

    //Sheep
    EntityKey(EntityType.SHEEP, null, 0) to EntityAttributes(
        "White Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkyZGYyMTZlY2QyNzYyNGFjNzcxYmFjZmJmZTAwNmUxZWQ4NGE3OWU5MjcwYmUwZjg4ZTljODc5MWQxZWNlNCJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 1) to EntityAttributes(
        "Orange Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI3MTQ0MmQ4YTM3ZGI0OWYwMmE5NGMyOTM1MjY5NDk2MmI1ZDBiZDZiZWEwNWYxZDkzZmUxOWViNGU3MDYwZSJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 2) to EntityAttributes(
        "Magenta Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUyMjhiMDRlOWI5NzlhMTBiNzBiOGRiNmYzZmIxOTlkZWViNTgxNTk0YTVhYTRhN2ZlYmU5NDhkYjE3MjI4YiJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 3) to EntityAttributes(
        "Light Blue Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhlYjBkMTc0Nzk4NzBiMzk3M2U4ZTAwMWI4MmRjZGUyMmVmYzlkMTBjOTA0MTJjNjczM2EwYjEzNjU2NGQxZiJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 4) to EntityAttributes(
        "Yellow Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJhNTM1NGMyMzBlODYxYWFjNzI3MzRhNDU4MmQxMzE3MDI2NDU0YjgwN2FjMzUzZmMzYTBiZDBkOGM0MjJiYSJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 5) to EntityAttributes(
        "Lime Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNlNDA5MGUxYmNjZjk5MmIzNmRlZjc0YTZkN2QzOTcyYzE3ZGIxYjc1NTU0ZTJjNTA5MjcxNjgwYjhlNzk3NCJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 6) to EntityAttributes(
        "Pink Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmU3Y2YxYzU4ZGJiN2MzMjU1Yjk0YzYwNDNmYThmMGQ3NzZjMTM0ZjRkOThiODFjYTMxNDEwOTY1ZjQ3YTI1YSJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 7) to EntityAttributes(
        "Gray Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTZjMmEyNzU1YjIwZGRmZjU1MWE2OTAzZjJkYzdlNjFmMTNlYmUzOWIxZDVjYTkyOWM4N2JkODU4M2VjODAxZiJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 8) to EntityAttributes(
        "Dark Gray Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRhNTliZTYyMGFlOGIzZWUwZGQwZmEyMmM4MGFmZmVkNGEwZjcyOTI5NWNiOGM0MWU3OGVlNzgzZjQ2MzNhZCJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 9) to EntityAttributes(
        "Cyan Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA1NTgzODdiNjY1OGY1ZTlkY2ZmYzcxOTIxNGI2MDNmNjAzYzRiMDRlNzA4YjdhYWJlNzViY2FlOTFlODA0YyJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 10) to EntityAttributes(
        "Purple Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQzY2JkYWUxZjIwYTc5MjgxZDNhNzFhZGYyNDJhMzVjOGNjNTg1NjJiNDE1ZjExMjBiY2E5ZDk0Yjc2ZjI1NCJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 11) to EntityAttributes(
        "Blue Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZWZjNGI0ZWFkZWM0ODU3NmE1NzAwZWM4MTIzOTU1MTAzMjdlNWQxZTdjMTA4ZmQ4YWJjNzc5NjY4NWFhMyJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 12) to EntityAttributes(
        "Brown Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTU4MTM3MTVjMmYzNGYwNTY0OWY4ZmEzZWFhYTY3ZjFlZGE1ZTZmOWNmOTMwZmE5YzJlMDQxMmQxZjk3MjhlMSJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 13) to EntityAttributes(
        "Green Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTc1M2E4ZWMzMmJlOWM1NTBkMWM1NjBhY2I5NDFlZGQ5ZTNiNzNkZGJmMTU4NjkyM2ZiMzdiMjIwYjQ1NTNkZCJ9fX0="
    ),
    EntityKey(EntityType.SHEEP, null, 14) to EntityAttributes(
        "Red Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBjZTViNWNhOTE2NWFjNzdhOWMzZTNmNjRkZjBkMzE3MGQ1YWZjZjlkNWE1NTc1ZTNmMGMwZjIxZTQzYjgzIn19fQ=="
    ),
    EntityKey(EntityType.SHEEP, null, 15) to EntityAttributes(
        "Black Sheep",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjM0YWM1YjM5OGNmN2M4NmUzZjZmMTg4YTUxMjdkOGIyODNkNzcyYmY1ODg1YzcwZTBjMTMwODA1ZjA2OTk1MCJ9fX0="
    ),

    //Mushroom
    EntityKey(EntityType.MOOSHROOM, null, null, "red") to EntityAttributes(
        "Red Mushroom",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI1Mjg0MWYyZmQ1ODllMGJjODRjYmFiZjllMWMyN2NiNzBjYWM5OGY4ZDZiM2RkMDY1ZTU1YTRkY2I3MGQ3NyJ9fX0="
    ),
    EntityKey(EntityType.MOOSHROOM, null, null, "brown") to EntityAttributes(
        "Brwon Mushroom",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODUwMTcwOGUyYzAwYTYwNWE5ODhjNDE5YWY3MGMxNjE3Y2U1Njg4NjI4Yjc0MTNjZmQzNzAzOGVjMDIyMWFiYyJ9fX0="
    ),

    //ZombieVillager
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "none") to EntityAttributes(
        "Zombie Villager",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyNDUzNzkwOTM1NDI1NjRlNWUxM2IwNjg2NTkzODFmYWQzMTk2MDQ2NzQyNWM5ODJjMmVhZTIwMjYzZjYwZCJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "armorer") to EntityAttributes(
        "Zombie Villager - Armorer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRlYjUzNDZmMmM3MDNiYmM0OTYzMDU4YzRkNzI0ZDZjNzEwMDE2MTI1MmEyZWI1NjljZmViZmQxMDY1ZTQ2YiJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "butcher") to EntityAttributes(
        "Zombie Villager - Butcher",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTdiMjk1ZWZiNjk5YjE1OGRmNzU2MzY0YWQyYjFkMGE3M2ViNGIzY2I5MDBjNGM3NTBkMTI0MTYzODIzNWZmYyJ9fX0="
    ),
    EntityKey(
        EntityType.ZOMBIE_VILLAGER,
        null,
        null,
        "cartographer"
    ) to EntityAttributes(
        "Zombie Villager - Cartographer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ4NWVhNjk0ZjY1ZTczZWI1NDZkYzJjNTg4Y2YyZDA3MDZiYzVlMDhjMTRlNzEyNWY5NzNiMzAzZmY0YzhlOCJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "cleric") to EntityAttributes(
        "Zombie Villager - Cleric",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg3MzY1MDQxMGQ4NTU3YTgxYTI3YjliN2Q4NTU4N2RkNWQwNGZhNzFkMmMwZjY4NjZjZTQ4NDMwMjUyMDA4ZSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "farmer") to EntityAttributes(
        "Zombie Villager - Farmer",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1ZTI0N2UwMzBlNjA3MGUyOGM5ZGUxYTRmNDcyNDUwNTIxZTU3M2RhNGQyNTkyMzVlNzAyYmRhMDkxMWE3NSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "fisherman") to EntityAttributes(
        "Zombie Villager - Fisherman",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWY4MGNjNGE1ZjFiYTZlNWEyOTdkZTdiZjRiMzY3ZGJkY2I5YTZmNDZjNmM2OGRhYjIxNWQ1OGQ5MmZmY2RkNSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "fletcher") to EntityAttributes(
        "Zombie Villager - Fletcher",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcyZDk4YzkxMDFjNjllNzRkN2JhNjUyMjlhOTQ5NzE2NTM1YmNhN2Q5ZjQxZWQzYTQwYjY1M2U3ZmM5MDNiNCJ9fX0="
    ),
    EntityKey(
        EntityType.ZOMBIE_VILLAGER,
        null,
        null,
        "leatherworker"
    ) to EntityAttributes(
        "Zombie Villager - Leatherworker",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2UzOGU4YWYwNzU3MDFhZDQwZGU1YjJlZjE4ZTFlZDM5Njc4ZTg1NTg0NDRlZTkxZDU2M2Q1ZTVhZWIzMmZhYiJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "librarian") to EntityAttributes(
        "Zombie Villager - Librarian",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFjNjg4OTdkNzE0OWFlNjdkYThmNGJhZDdhYzYzZmM3Y2JkYjk0YWY1MmRlMGU0YWQ3YjZkYjlmOWNmMzQ5YyJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "mason") to EntityAttributes(
        "Zombie Villager - Mason",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGEyN2U0MjBmNDRmOWJiMDZjN2IzNjg0MDJiMjJhZjZkYzU3YmY5ZThjYTM0NzU4NDBmNmY3NWUyMTc4MDljMSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "nitwit") to EntityAttributes(
        "Zombie Villager - Nitwit",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGM3NTA1ZjIyNGQ1MTY0YTExN2Q4YzY5ZjAxNWY5OWVmZjQzNDQ3MWM4YTJkZjkwNzA5NmM0MjQyYzM1MjRlOCJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "shepherd") to EntityAttributes(
        "Zombie Villager - Shepherd",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWY4MGNjNGE1ZjFiYTZlNWEyOTdkZTdiZjRiMzY3ZGJkY2I5YTZmNDZjNmM2OGRhYjIxNWQ1OGQ5MmZmY2RkNSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE_VILLAGER, null, null, "toolsmith") to EntityAttributes(
        "Zombie Villager - Toolsmith",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAwODkzZDMxYWNkMGU4NDIzMjkyZDQxMTNjZTkxZTI3M2QxZTU3ZTVlMTA0YmFhZmM4NTNiMmU4NjRjYjQzZiJ9fX0="
    ),
    EntityKey(
        EntityType.ZOMBIE_VILLAGER,
        null,
        null,
        "weaponsmith"
    ) to EntityAttributes(
        "Zombie Villager - Weaponsmith",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM1NGE0MTcyYTliYTljNDdmYjg1M2FiMjg0ZmRjMGEzNDQzMjYwMTNlNWQ3M2M0YmVjNzgwMGQ4M2Y0ZTM5OSJ9fX0="
    ),

    //Frog
    EntityKey(EntityType.FROG, frogVariant = FrogVariant.COLD) to EntityAttributes(
        "Cold Frog",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2U2MmU4YTA0OGQwNDBlYjA1MzNiYTI2YTg2NmNkOWMyZDA5MjhjOTMxYzUwYjQ0ODJhYzNhMzI2MWZhYjZmMCJ9fX0="
    ),
    EntityKey(EntityType.FROG, frogVariant = FrogVariant.TEMPERATE) to EntityAttributes(
        "Temperate Frog",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZTZmOTk5OGVkMmRhNzU3ZDFlNjM3MmYwNGVmYTIwZTU3ZGZjMTdjM2EwNjQ3ODY1N2JiZGY1MWMyZjJhMiJ9fX0="
    ),
    EntityKey(EntityType.FROG, frogVariant = FrogVariant.WARM) to EntityAttributes(
        "Warm Frog",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc3MzE0ZmEwMzhlYzMxMzU3ODQ1YTkzMjc0YjRkYzg4NDEyNDY4NjcyOGZmZTBkZWQ5YzM1NDY2YWNhMGFhYiJ9fX0="
    ),

    //Mobs without variants
    EntityKey(EntityType.ELDER_GUARDIAN) to EntityAttributes(
        "Elder Guardian",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkyMDg5NjE4NDM1YTBlZjYzZTk1ZWU5NWE5MmI4MzA3M2Y4YzMzZmE3N2RjNTM2NTE5OWJhZDMzYjYyNTYifX19"
    ),
    EntityKey(EntityType.STRAY) to EntityAttributes(
        "Stray",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhkZGY3NmU1NTVkZDVjNGFhOGEwYTVmYzU4NDUyMGNkNjNkNDg5YzI1M2RlOTY5ZjdmMjJmODVhOWEyZDU2In19fQ=="
    ),
    EntityKey(EntityType.HUSK) to EntityAttributes(
        "Husk",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzA5NjE2NGY4MTk1MGE1Y2MwZTMzZTg3OTk5Zjk4Y2RlNzkyNTE3ZjRkN2Y5OWE2NDdhOWFlZGFiMjNhZTU4In19fQ=="
    ),
    EntityKey(EntityType.SKELETON_HORSE) to EntityAttributes(
        "Skeleton Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ=="
    ),
    EntityKey(EntityType.ZOMBIE_HORSE) to EntityAttributes(
        "Zombie Horse",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIyOTUwZjJkM2VmZGRiMThkZTg2ZjhmNTVhYzUxOGRjZTczZjEyYTZlMGY4NjM2ZDU1MWQ4ZWI0ODBjZWVjIn19fQ=="
    ),
    EntityKey(EntityType.DONKEY) to EntityAttributes(
        "Donkey",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzk5YmI1MGQxYTIxNGMzOTQ5MTdlMjViYjNmMmUyMDY5OGJmOThjYTcwM2U0Y2MwOGI0MjQ2MmRmMzA5ZDZlNiJ9fX0="
    ),
    EntityKey(EntityType.MULE) to EntityAttributes(
        "Mule",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZkY2RhMjY1ZTU3ZTRmNTFiMTQ1YWFjYmY1YjU5YmRjNjA5OWZmZDNjY2UwYTY2MWIyYzAwNjVkODA5MzBkOCJ9fX0="
    ),
    EntityKey(EntityType.EVOKER) to EntityAttributes(
        "Evoker",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc5ZjEzM2E4NWZlMDBkM2NmMjUyYTA0ZDZmMmViMjUyMWZlMjk5YzA4ZTBkOGI3ZWRiZjk2Mjc0MGEyMzkwOSJ9fX0="
    ),
    EntityKey(EntityType.VEX) to EntityAttributes(
        "Vex",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ=="
    ),
    EntityKey(EntityType.ILLUSIONER) to EntityAttributes(
        "Illusioner",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNTEyZTdkMDE2YTIzNDNhN2JmZjFhNGNkMTUzNTdhYjg1MTU3OWYxMzg5YmQ0ZTNhMjRjYmViODhiIn19fQ=="
    ),
    EntityKey(EntityType.CREEPER) to EntityAttributes(
        "Creeper",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0="
    ),
    EntityKey(EntityType.WITHER_SKELETON) to EntityAttributes(
        "Wither Skeleton",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk1M2I2YzY4NDQ4ZTdlNmI2YmY4ZmIyNzNkNzIwM2FjZDhlMWJlMTllODE0ODFlYWQ1MWY0NWRlNTlhOCJ9fX0="
    ),
    EntityKey(EntityType.SKELETON) to EntityAttributes(
        "Skeleton",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNhNDQ1NzQ5MjUxYmRkODk4ZmI4M2Y2Njc4NDRlMzhhMWRmZjc5YTE1MjlmNzlhNDI0NDdhMDU5OTMxMGVhNCJ9fX0="
    ),
    EntityKey(EntityType.SPIDER) to EntityAttributes(
        "Spider",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg3YTk2YThjMjNiODNiMzJhNzNkZjA1MWY2Yjg0YzJlZjI0ZDI1YmE0MTkwZGJlNzRmMTExMzg2MjliNWFlZiJ9fX0="
    ),
    EntityKey(EntityType.GIANT) to EntityAttributes(
        "Giant",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ1MjhiMzIyOTY2MGYzZGZhYjQyNDE0ZjU5ZWU4ZmQwMWU4MDA4MWRkM2RmMzA4Njk1MzZiYTliNDE0ZTA4OSJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIE) to EntityAttributes(
        "Zombie",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ1MjhiMzIyOTY2MGYzZGZhYjQyNDE0ZjU5ZWU4ZmQwMWU4MDA4MWRkM2RmMzA4Njk1MzZiYTliNDE0ZTA4OSJ9fX0="
    ),
    EntityKey(EntityType.SLIME) to EntityAttributes(
        "Slime",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFhZmZkMzFlZmMzN2JhODRmNTAxODczOTRkODY4ODM0NGNjZDA2Y2RjOTI2ZGRmY2YyZGYxMTY5ODZkY2E5In19fQ=="
    ),
    EntityKey(EntityType.GHAST) to EntityAttributes(
        "Ghast",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU4YTM4ZTlhZmJkM2RhMTBkMTliNTc3YzU1YzdiZmQ2YjRmMmU0MDdlNDRkNDAxN2IyM2JlOTE2N2FiZmYwMiJ9fX0="
    ),
    EntityKey(EntityType.ZOMBIFIED_PIGLIN) to EntityAttributes(
        "Zombified Piglin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkzNTg0MmFmNzY5MzgwZjc4ZThiOGE4OGQxZWE2Y2EyODA3YzFlNTY5M2MyY2Y3OTc0NTY2MjA4MzNlOTM2ZiJ9fX0="
    ),
    EntityKey(EntityType.ENDERMAN) to EntityAttributes(
        "Enderman",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0="
    ),
    EntityKey(EntityType.CAVE_SPIDER) to EntityAttributes(
        "Cave Spider",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA0ZDVmY2IyODlmZTY1YjY3ODY2ODJlMWM3MzZjM2Y3YjE2ZjM5ZDk0MGUzZDJmNDFjZjAwNDA3MDRjNjI4MiJ9fX0="
    ),
    EntityKey(EntityType.SILVERFISH) to EntityAttributes(
        "Silverfish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ=="
    ),
    EntityKey(EntityType.BLAZE) to EntityAttributes(
        "Blaze",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="
    ),
    EntityKey(EntityType.MAGMA_CUBE) to EntityAttributes(
        "Magma Cube",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0="
    ),
    EntityKey(EntityType.ENDER_DRAGON) to EntityAttributes(
        "Ender Dragon",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZjZGFlNTg2YjUyNDAzYjkyYjE4NTdlZTQzMzFiYWM2MzZhZjA4YmFiOTJiYTU3NTBhNTRhODMzMzFhNjM1MyJ9fX0="
    ),
    EntityKey(EntityType.WITHER) to EntityAttributes(
        "Wither",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUyODBjZWZlOTQ2OTExZWE5MGU4N2RlZDFiM2UxODMzMGM2M2EyM2FmNTEyOWRmY2ZlOWE4ZTE2NjU4ODA0MSJ9fX0="
    ),
    EntityKey(EntityType.BAT) to EntityAttributes(
        "Bat",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU5OWRlZWY5MTlkYjY2YWMyYmQyOGQ2MzAyNzU2Y2NkNTdjN2Y4YjEyYjlkY2E4ZjQxYzNlMGEwNGFjMWNjIn19fQ=="
    ),
    EntityKey(EntityType.WITCH) to EntityAttributes(
        "Witch",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0="
    ),
    EntityKey(EntityType.ENDERMITE) to EntityAttributes(
        "Endermite",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0="
    ),
    EntityKey(EntityType.GUARDIAN) to EntityAttributes(
        "Guardian",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk1MjkwZTA5MGMyMzg4MzJiZDc4NjBmYzAzMzk0OGM0ZDAzMTM1MzUzM2FjOGY2NzA5ODgyM2I3ZjY2N2YxYyJ9fX0="
    ),
    EntityKey(EntityType.SHULKER) to EntityAttributes(
        "Shulker",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzM2E0YjczMjczYTY0YzhhYjI4MzBiMGZmZjc3N2E2MWE0ODhjOTJmNjBmODNiZmIzZTQyMWY0MjhhNDQifX19"
    ),
    EntityKey(EntityType.PIG) to EntityAttributes(
        "Pig",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0="
    ),
    EntityKey(EntityType.COW) to EntityAttributes(
        "Cow",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU4NDU2MTU1MTQyY2JlNGU2MTM1M2ZmYmFmZjMwNGQzZDljNGJjOTI0N2ZjMjdiOTJlMzNlNmUyNjA2N2VkZCJ9fX0="
    ),
    EntityKey(EntityType.CHICKEN) to EntityAttributes(
        "Chicken",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2EzNTgyY2U0ODg5MzMzZGFkMzI5ZTRlMjQzNzJhMDNhNWRhYTJjMzQyODBjNTYyNTZhZjUyODNlZGIwNDNmOCJ9fX0="
    ),
    EntityKey(EntityType.SQUID) to EntityAttributes(
        "Squid",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljMmM5Y2U2N2ViNTk3MWNjNTk1ODQ2M2U2YzlhYmFiOGU1OTlhZGMyOTVmNGQ0MjQ5OTM2YjAwOTU3NjlkZCJ9fX0="
    ),
    EntityKey(EntityType.OCELOT) to EntityAttributes(
        "Ocelot",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JiMjE0YTM0ODUyOWEwOTc5NTc0Yjg3YjA2YTQ4MGNjNjE3NzgxMGY3OTQ5MWNlOTgzZjE2ZGMzZDg0NDY2MiJ9fX0="
    ),
    EntityKey(EntityType.IRON_GOLEM) to EntityAttributes(
        "Iron Golem",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19"
    ),
    EntityKey(EntityType.POLAR_BEAR) to EntityAttributes(
        "Polar Bear",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRmZTkyNjkyMmZiYjQwNmYzNDNiMzRhMTBiYjk4OTkyY2VlNDQxMDEzN2QzZjg4MDk5NDI3YjIyZGUzYWI5MCJ9fX0="
    ),
    EntityKey(EntityType.TURTLE) to EntityAttributes(
        "Turtle",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ=="
    ),
    EntityKey(EntityType.PHANTOM) to EntityAttributes(
        "Phantom",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U5NTE1M2VjMjMyODRiMjgzZjAwZDE5ZDI5NzU2ZjI0NDMxM2EwNjFiNzBhYzAzYjk3ZDIzNmVlNTdiZDk4MiJ9fX0="
    ),
    EntityKey(EntityType.COD) to EntityAttributes(
        "Cod",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0="
    ),
    EntityKey(EntityType.SALMON) to EntityAttributes(
        "Salmon",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFlYjIxYTI1ZTQ2ODA2Y2U4NTM3ZmJkNjY2ODI4MWNmMTc2Y2VhZmU5NWFmOTBlOTRhNWZkODQ5MjQ4NzgifX19"
    ),
    EntityKey(EntityType.PUFFERFISH) to EntityAttributes(
        "Pufferfish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkyMzUwYzlmMDk5M2VkNTRkYjJjNzExMzkzNjMyNTY4M2ZmYzIwMTA0YTliNjIyYWE0NTdkMzdlNzA4ZDkzMSJ9fX0="
    ),
    EntityKey(EntityType.TROPICAL_FISH) to EntityAttributes(
        "Tropical Fish",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYzODlhY2Q3YzgyODBkMmM4MDg1ZTZhNmE5MWUxODI0NjUzNDdjYzg5OGRiOGMyZDliYjE0OGUwMjcxYzNlNSJ9fX0="
    ),
    EntityKey(EntityType.DROWNED) to EntityAttributes(
        "Drowned",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0="
    ),
    EntityKey(EntityType.DOLPHIN) to EntityAttributes(
        "Dolphin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ=="
    ),
    EntityKey(EntityType.PILLAGER) to EntityAttributes(
        "Pillager",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGFlZTZiYjM3Y2JmYzkyYjBkODZkYjVhZGE0NzkwYzY0ZmY0NDY4ZDY4Yjg0OTQyZmRlMDQ0MDVlOGVmNTMzMyJ9fX0="
    ),
    EntityKey(EntityType.RAVAGER) to EntityAttributes(
        "Ravager",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QyMGJmNTJlYzM5MGEwNzk5Mjk5MTg0ZmM2NzhiZjg0Y2Y3MzJiYjFiZDc4ZmQxYzRiNDQxODU4ZjAyMzVhOCJ9fX0="
    ),
    EntityKey(EntityType.WANDERING_TRADER) to EntityAttributes(
        "Wandering Trader",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk5ZDU4NWE5YWJmNTlmYWUyNzdiYjY4NGQyNDA3MGNlZjIxZTM1NjA5YTNlMThhOWJkNWRjZjczYTQ2YWI5MyJ9fX0="
    ),
    EntityKey(EntityType.BEE) to EntityAttributes(
        "Bee",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAxNzBkYTI0MjgxODhiOWIyN2E5NWQ2ODliNjkxZDVmZDc3ZDdhNjY2ZDExM2E5ZDJkNGU2NDJlNDc0YTkzNSJ9fX0="
    ),
    EntityKey(EntityType.HOGLIN) to EntityAttributes(
        "Hoglin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0="
    ),
    EntityKey(EntityType.PIGLIN) to EntityAttributes(
        "Piglin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDcxYjNhZWUxODJiOWE5OWVkMjZjYmY1ZWNiNDdhZTkwYzJjM2FkYzA5MjdkZGUxMDJjN2IzMGZkZjdmNDU0NSJ9fX0="
    ),
    EntityKey(EntityType.STRIDER) to EntityAttributes(
        "Strider",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0="
    ),
    EntityKey(EntityType.ZOGLIN) to EntityAttributes(
        "Zoglin",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3ZTE4NjAyZTAzMDM1YWQ2ODk2N2NlMDkwMjM1ZDg5OTY2NjNmYjllYTQ3NTc4ZDNhN2ViYmM0MmE1Y2NmOSJ9fX0="
    ),
    EntityKey(EntityType.PIGLIN_BRUTE) to EntityAttributes(
        "Piglin Brute",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzMDBlOTAyNzM0OWM0OTA3NDk3NDM4YmFjMjllM2E0Yzg3YTg0OGM1MGIzNGMyMTI0MjcyN2I1N2Y0ZTFjZiJ9fX0="
    ),
    EntityKey(EntityType.GLOW_SQUID) to EntityAttributes(
        "Glow Squid",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVlMmI0NmU1MmFjOTJkNDE5YTJkZGJjYzljZGNlN2I0NTFjYjQ4YWU3MzlkODVkNjA3ZGIwNTAyYTAwOGNlMCJ9fX0="
    ),
    EntityKey(EntityType.GOAT) to EntityAttributes(
        "Goat",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzMzMwMzk4YTBkODMzZjUzYWU4YzlhMWNiMzkzYzc0ZTlkMzFlMTg4ODU4NzBlODZhMjEzM2Q0NGYwYzYzYyJ9fX0="
    ),
    EntityKey(EntityType.WARDEN) to EntityAttributes(
        "Warden",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzZmNzQzNjFmYjAwNDkwYTBhOThlZWI4MTQ1NDRlY2RkNzc1Y2I1NTYzM2RiYjExNGU2MGQyNzAwNGNiMTAyMCJ9fX0="
    ),
    EntityKey(EntityType.ALLAY) to EntityAttributes(
        "Allay",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTFjNTlkY2NkZTRiODUzNTUwMGRjZjY3OTRjYTQ1MDY2M2Y2MDcyOTBlMjUxMGY2ZDhlYjFlNWViNzFkYTVhZiJ9fX0="
    ),
    EntityKey(EntityType.TADPOLE) to EntityAttributes(
        "Tadpole",
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTg3MDM1ZjUzNTIzMzRjMmNiYTZhYzRjNjVjMmI5MDU5NzM5ZDZkMGU4MzljMWRkOThkNzVkMmU3Nzk1Nzg0NyJ9fX0="
    )
)

val availableInSurvival = listOf(
    EntityKey(EntityType.WITHER_SKELETON),
    EntityKey(EntityType.SKELETON),
    EntityKey(EntityType.ZOMBIE),
    EntityKey(EntityType.CREEPER)
)

val onlyAvailableWithCheats = listOf(
    EntityKey(EntityType.ILLUSIONER),
    EntityKey(EntityType.ZOMBIE_HORSE),
    EntityKey(EntityType.GIANT)
)

fun Entity.getHead(): MobHead {
    val key: EntityKey
    when (this.type) {
        EntityType.CAT -> {
            val cat = this as Cat
            key = EntityKey(this.type, null, cat.collarColor.id, null, cat.catVariant)
        }
        EntityType.ZOMBIE_VILLAGER -> {
            val zombieVillager = this as ZombieVillager
            key = EntityKey(this.type, null, null, zombieVillager.villagerData.profession.name)
        }
        EntityType.MOOSHROOM -> {
            val mushroomCow = this as MushroomCow
            key = EntityKey(this.type, null, null, mushroomCow.mushroomType.name)
        }
        EntityType.SHEEP -> {
            val sheep = this as Sheep
            key = EntityKey(this.type, null, sheep.color.id)
        }
        EntityType.WOLF -> {
            val wolf = this as Wolf
            key = EntityKey(this.type, null, wolf.collarColor.id)
        }
        EntityType.SNOW_GOLEM -> {
            val snowman = this as SnowGolem
            key = EntityKey(this.type, if (snowman.hasPumpkin()) 0 else 1)
        }
        EntityType.HORSE -> {
            val horse = this as Horse
            key = EntityKey(this.type, horse.variant.id)
        }
        EntityType.RABBIT -> {
            val rabbit = this as Rabbit
            key = EntityKey(this.type, rabbit.rabbitType)
        }
        EntityType.LLAMA -> {
            val llama = this as Llama
            key = EntityKey(this.type, llama.variant)
        }
        EntityType.TRADER_LLAMA -> {
            val llama = this as TraderLlama
            key = EntityKey(this.type, llama.variant)
        }
        EntityType.PARROT -> {
            val parrot = this as Parrot
            key = EntityKey(this.type, parrot.variant)
        }
        EntityType.VILLAGER -> {
            val villager = this as Villager
            key = EntityKey(this.type, null, null, villager.villagerData.profession.name)
        }
        EntityType.PANDA -> {
            val panda = this as Panda
            key = EntityKey(this.type, panda.mainGene.id)
        }
        EntityType.FOX -> {
            val fox = this as Fox
            key = EntityKey(this.type, fox.foxType.id)
        }
        EntityType.AXOLOTL -> {
            val axolotl = this as Axolotl
            key = EntityKey(this.type, axolotl.variant.id)
        }
        EntityType.FROG -> {
            val frog = this as Frog
            key = EntityKey(this.type, frogVariant = frog.variant)
        }
        else -> {
            key = EntityKey(this.type)
        }
    }

    val head = itemStack(Items.PLAYER_HEAD){
        setSkullTexture(textures[key]?.texture ?: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFiNzM5ZjRjYjAyOGVmMmFjZjM0YTZkYzNiNGZmODVlYWM1Y2E5ODdiNTgzMmJmZGQwZjNjMzM1MWFhNDQzIn19fQ==")
    }

    return MobHead(
        onlyAvailableWithCheats.contains(key),
        availableInSurvival.contains(key),
        head,
        key,
        textures[key] ?: EntityAttributes("Unknown", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFiNzM5ZjRjYjAyOGVmMmFjZjM0YTZkYzNiNGZmODVlYWM1Y2E5ODdiNTgzMmJmZGQwZjNjMzM1MWFhNDQzIn19fQ==")
    )
}

