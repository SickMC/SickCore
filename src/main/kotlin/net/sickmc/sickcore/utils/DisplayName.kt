package net.sickmc.sickcore.utils

import net.minecraft.network.chat.BaseComponent

data class DisplayName(val full: BaseComponent, val kyoriFull: net.kyori.adventure.text.Component, val prefix: String, val name: String, val color: Int)
