package net.sickmc.sickcore.language

import net.kyori.adventure.text.Component
import net.minecraft.network.chat.MutableComponent

class LanguageEntry(val raw: String) {

    var styler: LanguageEntryStyler? = null
    var replacements = arrayListOf<Pair<String, String>>()

    inline fun style(crossinline style: LanguageEntryStyler.() -> Unit) {
        styler = LanguageEntryStyler(this).apply(style)
    }

    fun replace(vararg replacement: Pair<String, String>){
        replacements += replacement
    }

    fun buildReplaced(): String{
        val replaced = raw
        replacements.forEach {
            replaced.replace(it.first, it.second)
        }
        return replaced
    }

    fun build(): MutableComponent = LanguageEntryVisualizer(this).build()
    fun buildAdventure(): Component = LanguageEntryVisualizer(this).buildAdventure()

}