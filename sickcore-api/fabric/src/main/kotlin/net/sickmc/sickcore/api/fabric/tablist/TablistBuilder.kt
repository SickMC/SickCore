package net.sickmc.sickcore.api.fabric.tablist

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.silkmc.silk.core.annotations.InternalSilkApi
import net.silkmc.silk.game.sideboard.SideboardLine

/**
 * Opens a new tablist [builder] and returns the final tablist.
 *
 * @param builder the [TablistBuilder]
 *
 * @return the final instance of [Tablist]
 */
@OptIn(InternalSilkApi::class)
inline fun tablist(
    builder: TablistBuilder.() -> Unit
): Tablist {
    val tablistBuilder = TablistBuilder().apply(builder)
    return Tablist(
        tablistBuilder.nameGenerator, tablistBuilder.headerLines, tablistBuilder.footerLines
    )
}

/**
 * A helper class which is used to create a [Tablist].
 *
 * You probably want to use this class via the [tablist] function.
 */
class TablistBuilder {

    @PublishedApi
    internal val headerLines = ArrayList<SideboardLine>()

    @PublishedApi
    internal val footerLines = ArrayList<SideboardLine>()

    @PublishedApi
    internal var nameGenerator: (suspend ServerPlayer.() -> Pair<Component, String>)? = null

    /**
     * Adds the name generator to the [Tablist]
     *
     * @param nameGenerator the generator to get the name and the priority of the player
     */
    fun generateName(nameGenerator: suspend ServerPlayer.() -> Pair<Component, String>) {
        this@TablistBuilder.nameGenerator = nameGenerator
    }

    /**
     * Adds footer lines implementing the [SideboardLine] interface.
     *
     * @param lines the footer lines of the tablist
     */
    fun footer(lines: List<SideboardLine>) {
        footerLines += lines
    }

    /**
     * Adds header lines implementing the [SideboardLine] interface.
     *
     * @param lines the header lines of the tablist
     */
    fun header(lines: List<SideboardLine>) {
        headerLines += lines
    }
}