@file:OptIn(ExperimentalTime::class)

package net.sickmc.sickcore.games.survival

import net.axay.fabrik.core.Fabrik
import net.axay.fabrik.core.text.literal
import net.axay.fabrik.core.text.literalText
import net.axay.fabrik.igui.*
import net.axay.fabrik.igui.observable.toGuiList
import net.axay.fabrik.igui.observable.toMutableGuiList
import net.minecraft.world.item.Items
import net.sickmc.sickcore.utils.Colors
import net.sickmc.sickcore.utils.fabric.*
import org.bson.Document
import kotlin.time.ExperimentalTime

fun openHeadGUI(player: SurvivalPlayer){

    val allHeads = textures.filter { !onlyAvailableWithCheats.contains(it.key) && !availableInSurvival.contains(it.key) }.map { it.value }.toMutableList()
    allHeads.addAll(extraHeads)
    val headDocument = player.gameDocument.get("heads", Document::class.java)

    val gui = igui(GuiType.NINE_BY_SIX, literalText("Mob Heads"){color = Colors.GOLD}, 1){
        page(1) {
            effectFrom = GuiPage.ChangeEffect.SLIDE_VERTICALLY

            placeholder(Slots.RowOne, Items.WHITE_STAINED_GLASS_PANE.guiIcon)

            val compound = compound(
                (2 sl 2) rectTo (6 sl 8),
                allHeads.toGuiList(),
                iconGenerator = {
                    if (!headDocument.getBoolean(it.name.replace(" ", "_"))) createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFiNzM5ZjRjYjAyOGVmMmFjZjM0YTZkYzNiNGZmODVlYWM1Y2E5ODdiNTgzMmJmZGQwZjNjMzM1MWFhNDQzIn19fQ==").setHoverName(it.getDisplayName())
                    else createHead(it.texture).setHoverName(it.getDisplayName())
                }
            )

            compoundScrollForwards(1 sl 9, CommonHeads.ARROW_FORWARDS.setHoverName(literalText("Forwards"){color = Colors.LIGHT_GRAY}).guiIcon , compound)
            compoundScrollBackwards(1 sl 1, CommonHeads.ARROW_BACKWARDS.setHoverName(literalText("Backwards"){color = Colors.LIGHT_GRAY}).guiIcon , compound)
        }
    }
    Fabrik.currentServer?.playerList?.getPlayer(player.sickPlayer.uniqueID)?.openGui(gui, 1)
}