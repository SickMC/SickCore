package net.sickmc.sickcore.language

import net.sickmc.sickcore.utils.file.asDocument
import org.bson.Document

object LanguageManager {

    val languageCache = hashMapOf<Language, Document>()

    fun loadLanguages() {
        listOf(
            Language("en", "UK"), Language("en", "US"), Language("de", "DE"), Language("fr", "FR")
        ).forEach {
            reloadLanguage(it)
        }
    }

    fun reloadLanguage(language: Language) {
        val file = this.javaClass.classLoader.getResourceAsStream("${language.name}_${language.state}.json")
        if (file != null) languageCache[language] = file.asDocument()
    }

    fun Language.getEntry(key: String): LanguageEntry {
        val document = languageCache[this] ?: error("Language ${this.name}_${this.state} cannot be found!")
        return LanguageEntry(document.getString(key))
    }

}