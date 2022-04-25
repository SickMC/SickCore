package net.sickmc.sickcore.utils.mongo

import com.mongodb.client.model.Filters
import org.bson.Document

class MongoCollection(name: String) {

    val collection = MongoConnection.db.getCollection<Document>(name)
    suspend fun getDocument(key: String, value: String): MongoDocument?{
        return if(collection.find(Filters.eq(key, value)).first() == null) null
        else MongoDocument(collection, key, value, collection.find(Filters.eq(key, value)).first()!!)
    }

    suspend fun getOrCreateDocument(key: String, value: String, document: Document): MongoDocument{
        if (collection.find(Filters.eq(key, value)).first() == null)collection.insertOne(document)
        return MongoDocument(collection, key, value, document)
    }

    suspend fun removeDocument(key: String, value: String){
        if (collection.find(Filters.eq(key, value)).first() == null)return
        collection.deleteOne(Filters.eq(key, value))
    }

    suspend fun createDocument(document: Document){
        collection.insertOne(document)
    }

    suspend fun getAndCreateDocument(key: String, value: String, document: Document): MongoDocument{
        if (collection.find(Filters.eq(key, value)).first() == null)createDocument(document)
        return MongoDocument(collection, key, value, document)
    }

}