package net.sickmc.sickcore.utils.redis

import io.github.crackthecodeabhi.kreds.connection.*

var kreds: KredsClient = newClient(Endpoint.from("${System.getenv("REDIS_ADDRESS")}:${System.getenv("REDIS_PORT")}"))