package net.sickmc.sickcore.utils.mongo

import com.mongodb.client.model.Filters
import kotlinx.coroutines.flow.Flow
import org.bson.Document
import org.litote.kmongo.coroutine.CoroutineCollection

suspend fun CoroutineCollection<Document>.retrieveOne(key: String, value: String): Document?{
    return this.find(Filters.eq(key, value)).first()
}

fun CoroutineCollection<Document>.retrieveMany(key: String, value: String): Flow<Document>{
    return this.find(Filters.eq(key, value)).toFlow()
}

suspend fun CoroutineCollection<Document>.replace(key: String, value: String, doc: Document){
    this.replaceOne(Filters.eq(key, value), doc)
}