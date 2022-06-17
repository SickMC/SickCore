package net.sickmc.sickcore.utils

import java.util.UUID

const val test = false
fun String.toUUID(): UUID = UUID.fromString(this)

fun String.transformToUUID(): UUID{
    val chars: ArrayList<String> = this.split("") as ArrayList<String>
    val newUUID = StringBuilder()
    var currentIndex = 0
    for (char in chars) {
        when(currentIndex){
            8, 13, 17, 22 -> {
                newUUID.append("-")
            }
            else -> {
                newUUID.append(char)
            }
        }
        currentIndex++
    }
    return newUUID.toString().toUUID()
}