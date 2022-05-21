package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.sickmc.sickcore.utils.FileUtils

var kreds: KredsClient = newClient(Endpoint.from("${System.getenv("REDIS_ADDRESS")}:${System.getenv("REDIS_PORT")}"))