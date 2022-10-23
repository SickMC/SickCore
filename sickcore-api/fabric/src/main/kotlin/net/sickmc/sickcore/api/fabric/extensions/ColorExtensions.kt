package net.sickmc.sickcore.api.fabric.extensions

import net.sickmc.sickapi.util.Gradient
import java.awt.Color

fun Gradient.colorOf(percent: Double): Int {
    val color1 = Color(startColor)
    val color2 = Color(endColor)
    val inversePercent: Double = 1.0 - percent
    val color = Color(
        (color1.red * percent + color2.red * inversePercent).toInt(),
        (color1.green * percent + color2.green * inversePercent).toInt(),
        (color1.blue * percent + color2.blue * inversePercent).toInt()
    )
    return color.red * 65536 + color.green * 256 + color.blue
}