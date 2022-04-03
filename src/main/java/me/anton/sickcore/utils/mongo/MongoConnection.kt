package me.anton.sickcore.utils.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import me.anton.sickcore.utils.FileUtils
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class MongoConnection {

    private val credentials = FileUtils.getFileAsDocument("mongo")
    private val connectionString = ConnectionString("mongodb://${credentials.getString("username")}:${credentials.getString("password")}@${credentials.getString("address")}:${credentials.getInteger("port")}/?authSource=${credentials.getString("databaseName")}")
    private val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
    val client: CoroutineClient = KMongo.createClient(settings).coroutine

    val db = client.getDatabase(credentials.getString("databaseName"))

}