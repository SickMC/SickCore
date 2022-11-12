package net.sickmc.sickcore.survival

import kotlinx.coroutines.*
import net.sickmc.sickcore.api.fabric.server
import net.silkmc.silk.core.text.broadcastText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

object AutoRestart {
    fun init() {
        CoroutineScope(Dispatchers.Default + SupervisorJob()).launch {
            delay(150.minutes)
            sendRestartMessage(30.minutes)
            delay(15.minutes)
            sendRestartMessage(15.minutes)
            delay(5.minutes)
            sendRestartMessage(10.minutes)
            delay(5.minutes)
            sendRestartMessage(5.minutes)
            delay(2.minutes)
            sendRestartMessage(3.minutes)
            delay(1.minutes)
            sendRestartMessage(2.minutes)
            delay(1.minutes)
            sendRestartMessage(1.minutes)
            delay(30.seconds)
            repeat(30) {
                sendRestartMessage((30 - it).seconds)
            }
            server.halt(false)
        }
    }

    private fun sendRestartMessage(timeLeft: Duration) {
        server.broadcastText("The server restarts in ") {
            color = 0xF9E99F
            text(timeLeft.toString()) {
                color = 0xF9B98D
            }
            text("!") {
                color = 0xF9E99F
            }
        }
    }
}