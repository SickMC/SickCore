package net.sickmc.sickcore.games.survival

import net.sickmc.sickcore.games.Game
import net.sickmc.sickcore.utils.fabric.EntityAttributes
import net.sickmc.sickcore.utils.fabric.MobHeadRarity

val extraHeads = arrayListOf(
    EntityAttributes("Ice Sheep", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYzNDJjYmUyOGJlMjBjY2ZjNjhhZTUwYjg4ZGVlOWIzNzM4NzQwOTBjYzcwNzk3ZjNmZmNkYzAwYTgyZTRjIn19fQ==", MobHeadRarity.SPECIAL),
    EntityAttributes("Hooded Enderman", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYxMWI4MWI3ODRhNzc0NTlhOWY5ZDI1MDk0YjhiMmUzZDg5MDI4ZTFlN2JiYWJlOTE2NjVjZDJkYzY2NiJ9fX0=", MobHeadRarity.SPECIAL),
    EntityAttributes("Paimon", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM1MWNlOWE2NjRkNThmN2JhOTM2NTIyOTQzMGMzMDNkMTYwMjM3NjFiNzBkZDExZTM3YWE5NjVjZGZmYjQ3YiJ9fX0=", MobHeadRarity.SPECIAL),
    EntityAttributes("Herobrine", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=", MobHeadRarity.MYTHIC)
)
class Survival : Game() {

    override val name: String = "Survival"
    override val startEnvironment: Array<String> = arrayOf("public_survival_latest")

    override suspend fun enable() {
        SurvivalCommands.register()
        CommonEvents.register()
    }

    override suspend fun disable() {

    }

}