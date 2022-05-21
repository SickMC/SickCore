package net.sickmc.sickcore.utils.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.sickmc.sickcore.utils.FileUtils
import org.bson.Document
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
val mongoClient = KMongo.createClient(MongoClientSettings.builder()
    .applyConnectionString(ConnectionString("mongodb://${System.getenv("MONGO_USERNAME")}:${System.getenv("MONGO_PASSWORD")}@${System.getenv("MONGO_ADDRESS")}:${System.getenv("MONGO_PORT")}/?authSource=${System.getenv("MONGO_DATABASE")}"))
    .build()).coroutine
val db = mongoClient.getDatabase(System.getenv("MONGO_DATABASE"))

val players = db.getCollection<Document>("sickPlayers")
val staffColl = db.getCollection<Document>("staff")
val rankColl = db.getCollection<Document>("ranks")
val rankGroupColl = db.getCollection<Document>("rankGroups")
val configColl = db.getCollection<Document>("config")
val survivalColl = db.getCollection<Document>("survival")