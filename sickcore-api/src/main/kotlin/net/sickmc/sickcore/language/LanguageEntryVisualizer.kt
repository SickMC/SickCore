package net.sickmc.sickcore.language

import net.kyori.adventure.text.Component
import net.minecraft.network.chat.MutableComponent
import net.silkmc.silk.core.text.literalText

class LanguageEntryVisualizer(val entry: LanguageEntry) {

    fun build(): MutableComponent {
        if (entry.styler != null) {
            val styler = entry.styler!!
            if (styler.siblings.isEmpty())
                return literalText(entry.buildReplaced()) {
                    color = styler.wholeColor
                    bold = styler.wholeBold
                    obfuscated = styler.wholeObfuscated
                    underline = styler.wholeUnderlined
                    strikethrough = styler.wholeStrikethrough
                    italic = styler.wholeItalic
                }


        }
        return literalText(entry.buildReplaced())
    }

    fun buildAdventure(): Component {
        if (entry.styler != null) {

        }
        return Component.text(entry.buildReplaced())
    }

}