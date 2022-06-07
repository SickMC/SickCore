package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*

var kreds: KredsClient = newClient(Endpoint.from("${System.getenv("REDIS_ADDRESS")}:${System.getenv("REDIS_PORT")}"))
//var kreds: KredsClient = newClient(Endpoint.from("${System.getProperty("REDIS_ADDRESS")}:${System.getProperty("REDIS_PORT")}"))