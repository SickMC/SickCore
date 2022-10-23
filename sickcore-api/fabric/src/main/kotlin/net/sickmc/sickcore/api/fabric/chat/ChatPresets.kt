package net.sickmc.sickcore.api.fabric.chat

import kotlinx.coroutines.runBlocking
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.sickmc.sickapi.util.Colors
import net.sickmc.sickcore.api.fabric.extensions.displayName
import net.sickmc.sickcore.api.fabric.extensions.sickPlayer
import net.silkmc.silk.core.text.literal
import net.silkmc.silk.core.text.literalText

object ChatPresets {
    val onlyNames
        get() = chat {
            chat { player, message ->
                ((player.sickPlayer()?.displayName ?: player.displayName) as MutableComponent).append(literalText("  "))
                    .append(message.plain.literal.apply {
                        withStyle(
                            Style.EMPTY.withColor(Colors.white)
                        )
                    })
            }

            join {
                ((it.sickPlayer()?.displayName
                    ?: it.displayName) as MutableComponent).append(literalText(" joined the server!").apply {
                    withStyle(
                        Style.EMPTY.withColor(Colors.white)
                    )
                })
            }

            quit {
                ((it.sickPlayer()?.displayName
                    ?: it.displayName) as MutableComponent).append(literalText(" quit the server!").apply {
                    withStyle(
                        Style.EMPTY.withColor(Colors.white)
                    )
                })
            }

            advancement { player, advancement ->
                Component.translatable(
                    "chat.type.advancement." + advancement.display!!.frame.getName(),
                    player.sickPlayer()?.displayName ?: player.displayName,
                    advancement.chatComponent
                )
            }

            deathName { player, _ ->
                runBlocking {
                    player.sickPlayer()?.displayName ?: player.displayName
                }
            }
        }

    val nothing
        get() = chat {
            join { Component.empty() }
            quit { Component.empty() }
            advancement { _, _ -> Component.empty() }
            death { _, _ -> Component.empty() }
        }
}