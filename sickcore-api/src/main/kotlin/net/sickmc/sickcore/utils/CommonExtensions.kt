package net.sickmc.sickcore.utils

import java.util.*

val test = System.getProperty("test") != null
fun String.toUUID(): UUID = UUID.fromString(this)