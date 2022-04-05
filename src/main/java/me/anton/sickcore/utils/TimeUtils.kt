package me.anton.sickcore.utils

import kotlin.time.Duration.Companion.milliseconds

class TimeUtils {

    companion object{
        fun formatTime(time: Long): String{
            time.milliseconds.toComponents { days, hours, minutes, seconds, nanoseconds ->
                return "${days}d ${hours}h ${minutes}m ${seconds}s ${nanoseconds}ns"
            }
        }
    }

}