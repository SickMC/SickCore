package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*
import net.sickmc.sickcore.utils.test

var kreds: KredsClient = if (!test) newClient(Endpoint.from("${System.getenv("REDIS_ADDRESS")}:${System.getenv("REDIS_PORT")}")) else newClient(Endpoint.from("${System.getProperty("REDIS_ADDRESS")}:${System.getProperty("REDIS_PORT")}"))