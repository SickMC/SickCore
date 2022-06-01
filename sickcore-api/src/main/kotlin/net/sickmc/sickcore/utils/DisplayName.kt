package net.sickmc.sickcore.utils

import net.minecraft.network.chat.MutableComponent

data class DisplayName(val full: MutableComponent, val kyoriFull: net.kyori.adventure.text.Component, val prefix: String, val name: String, val color: Int)
