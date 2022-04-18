package net.sickmc.sickcore.utils.mongo

import com.mongodb.client.model.Filters
import org.bson.Document
import org.litote.kmongo.coroutine.CoroutineCollection

class MongoDocument(val collection: CoroutineCollection<Document>, val key: String, val value: String, var document: Document) {

    suspend fun save(){
        if (collection.find(Filters.eq(key, value)).first() == null) collection.insertOne(document)
        else collection.replaceOne(Filters.eq(key, value), document)
    }

}