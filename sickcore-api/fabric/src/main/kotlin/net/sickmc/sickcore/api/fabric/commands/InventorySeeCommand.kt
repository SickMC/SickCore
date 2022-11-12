package net.sickmc.sickcore.api.fabric.commands

import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.GameProfileArgument
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.inventory.ContainerListener
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import net.sickmc.sickcore.api.fabric.server
import net.silkmc.silk.commands.LiteralCommandBuilder
import net.silkmc.silk.commands.PermissionLevel
import net.silkmc.silk.commands.command

val invSeeCommand = command("invsee") {
    invCommand(false)
}

val enderChestSeeCommand = command("enderchestsee") {
    alias("ecsee")
    invCommand(true)
}

private fun LiteralCommandBuilder<CommandSourceStack>.invCommand(ec: Boolean) {
    requiresPermissionLevel(PermissionLevel.BAN_RIGHTS)
    argument("target", GameProfileArgument.gameProfile()) { targetPl ->
        runs {
            val player = source.playerOrException
            val targetProfile = targetPl().getNames(source).first()
            var offline = false
            val target = server.playerList.getPlayer(targetProfile.id) ?: ServerPlayer(
                server, server.overworld(), targetProfile, null
            ).also {
                server.playerList.load(it)
                offline = true
            }

            player.openMenu(SimpleMenuProvider({ i: Int, playerInventory, _ ->
                ChestMenu(
                    if (ec) MenuType.GENERIC_9x3 else MenuType.GENERIC_9x4,
                    i,
                    playerInventory,
                    if (ec) target.enderChestInventory else target.inventory,
                    if (ec) 3 else 4
                ).apply {
                    addSlotListener(object : ContainerListener {
                        override fun slotChanged(
                            abstractContainerMenu: AbstractContainerMenu, i: Int, itemStack: ItemStack
                        ) {
                            if (offline) {
                                server.playerList.players.add(target)
                                server.playerList.saveAll()
                                server.playerList.players.remove(target)
                            }
                        }

                        override fun dataChanged(abstractContainerMenu: AbstractContainerMenu, i: Int, j: Int) {}
                    })
                }
            }, target.name))
        }
    }
}