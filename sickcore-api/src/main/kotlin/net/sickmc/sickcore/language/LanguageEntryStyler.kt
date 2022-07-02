package net.sickmc.sickcore.language

import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.network.chat.MutableComponent
import net.silkmc.silk.core.text.literalText

class LanguageEntryStyler(val entry: LanguageEntry) {

    var wholeColor = 0xFFFFFF

    var wholeBold = false
    var wholeUnderlined = false
    var wholeObfuscated = false
    var wholeStrikethrough = false
    var wholeItalic = false

    var siblings = hashMapOf<Int, LanguageEntrySiblingStyler>()

}

class LanguageEntrySiblingStyler(val replaced: String) {

    var colorOverride = 0xFFFFF
    var boldOverride = false
    var underlinedOverride = false
    var obfuscatedOverride = false
    var strikethroughOverride = false
    var italicOverride = false

    fun build(): MutableComponent = literalText(replaced) {
        color = colorOverride
        bold = boldOverride
        underline = underlinedOverride
        obfuscated = obfuscatedOverride
        strikethrough = strikethroughOverride
        italic = italicOverride
    }

    fun buildAdventure(): net.kyori.adventure.text.Component {
        val component = net.kyori.adventure.text.Component.text(replaced).color { colorOverride }
        if (boldOverride) component.decoration(TextDecoration.BOLD)
        if (underlinedOverride) component.decoration(TextDecoration.UNDERLINED)
        if (obfuscatedOverride) component.decoration(TextDecoration.OBFUSCATED)
        if (strikethroughOverride) component.decoration(TextDecoration.STRIKETHROUGH)
        if (italicOverride) component.decoration(TextDecoration.ITALIC)
        return component
    }

}