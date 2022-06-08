package net.sickmc.sickcore.utils.fabric

import net.minecraft.world.phys.Vec3
import kotlin.math.roundToInt

fun Vec3.toPrettyString(): String{
    return "(${this.x.roundToInt()}, ${this.y.roundToInt()}, ${this.z.roundToInt()})"
}