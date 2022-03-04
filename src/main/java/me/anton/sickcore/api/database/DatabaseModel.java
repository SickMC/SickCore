package me.anton.sickcore.api.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.anton.sickcore.core.Core;
import org.bson.Document;

import java.util.*;

@Getter
public class DatabaseModel {

    private final MongoCollection<Document> collection;

    public DatabaseModel(String name){
        MongoDatabase database = Core.getInstance().getMongoConnection().getDatabase();
        if (!database.listCollectionNames().into(new ArrayList<>()).contains(name))database.createCollection(name);
        this.collection = database.getCollection(name);
    }

    public Document createDocument(Document document){
        if (documentExists(document))return document;
        collection.insertOne(document);
        return document;
    }

    public void updateDocument(Finder finder, Document document){
        if (!documentExists(collection.find(finder.bson()).first()))createDocument(document);
        collection.replaceOne(finder.bson(), document);
    }

    public void updateDocument(String key, String value, Document document){
        if (!documentExists(collection.find(Filters.eq(key, value)).first()))createDocument(document);
        collection.replaceOne(Filters.eq(key, value), document);
    }

    public Object get(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().get(required);
    }

    public Object get(String required, Finder finder){
        return collection.find(finder.bson()).first().get(required);
    }

    public Object get(String required, Document finder){
        return collection.find(finder).first().get(required);
    }

    public boolean documentExists(Document document){
        return collection.find(document).first() != null;
    }

    public boolean documentExists(Finder finder){
        return collection.find(finder.bson()).first() != null;
    }

    public Document getDocument(String key, String value){
        return collection.find(Filters.eq(key, value)).first();
    }

    public Document getDocument(Finder finder){
        return collection.find(finder.bson()).first();
    }

    public Document getOrCreate(Finder finder){
        if (documentExists(finder))return getDocument(finder);
        return createDocument(new Document(finder.getKey(), finder.getKey()));
    }

    public List<Document> getAllValues(){
        return collection.find().into(new ArrayList<>());
    }

    public void deleteDocument(Finder finder){
        collection.deleteOne(finder.bson());
    }
}
