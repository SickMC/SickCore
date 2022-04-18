package net.sickmc.sickcore.utils.mongo

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.sickmc.sickcore.utils.FileUtils
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

object MongoConnection {

    private val credentials = FileUtils.getFileAsDocument("mongo")
    private val settings = MongoClientSettings.builder()
        .credential(MongoCredential.createCredential(credentials.getString("username"), credentials.getString("databaseName"), credentials.getString("password").toCharArray()))
        .applyToClusterSettings{it.hosts(listOf(ServerAddress(credentials.getString("address"))))}
        .build()
    val client: CoroutineClient = KMongo.createClient(settings).coroutine

    val db = client.getDatabase(credentials.getString("databaseName"))

}