package net.sickmc.sickcore.utils.file

import org.bson.Document
import java.io.File
import java.io.InputStream

fun File.asDocument(): Document {
    val reader = this.bufferedReader()
    val inputString = reader.use { it.readText() }
    return Document.parse(inputString)
}

fun InputStream.asDocument(): Document {
    return Document.parse(this.bufferedReader().use { it.readText() })
}