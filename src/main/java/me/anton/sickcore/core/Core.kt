package me.anton.sickcore.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.anton.sickcore.utils.mongo.MongoCollection
import me.anton.sickcore.utils.mongo.MongoConnection

abstract class Core {

    companion object{
        lateinit var instance: Core
    }

    val connection = MongoConnection()
    val configCollection = MongoCollection("config")
    val databaseScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        instance = this
    }

}