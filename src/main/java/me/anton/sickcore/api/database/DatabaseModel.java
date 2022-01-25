package me.anton.sickcore.api.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.anton.sickcore.core.Core;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Getter
public class DatabaseModel {

    private MongoCollection<Document> collection;

    public DatabaseModel(String name){
        MongoDatabase database = Core.getInstance().getMongoConnection().getDatabase();
        if (!database.listCollectionNames().into(new ArrayList<>()).contains(name))database.createCollection(name);
        this.collection = database.getCollection(name);
    }

    public void createDocument(Document document){
        if (documentExists(document))return;
        collection.insertOne(document);
    }

    public void updateDocument(String key, String value, Document document){
        if (!documentExists(collection.find(Filters.eq(key, value)).first()))createDocument(document);
        collection.replaceOne(Filters.eq(key, value), document);
    }

    public String getString(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getString(required);
    }
    public String getString(String key, Document finder){
        return collection.find(finder).first().getString(key);
    }

    public int getInteger(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getInteger(required);
    }
    public int getInteger(String key, Document finder){
        return collection.find(finder).first().getInteger(key);
    }

    public double getDouble(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getDouble(required);
    }
    public double getDouble(String key, Document finder){
        return collection.find(finder).first().getDouble(key);
    }

    public Date getDate(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getDate(required);
    }

    public Date getDate(String key, Document finder){
        return collection.find(finder).first().getDate(key);
    }

    public boolean getBoolean(String key, Document finder){
        return collection.find(finder).first().getBoolean(key);
    }

    public boolean getBoolean(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getBoolean(required);
    }

    public long getLong(String required, String key, String value){
        return collection.find(Filters.eq(key, value)).first().getLong(required);
    }

    public long getLong(String key, Document finder){
        return collection.find(finder).first().getLong(key);
    }

    public boolean documentExists(Document document){
        return collection.find(document).first() != null;
    }

    public boolean documentExists(String key, String value){
        return collection.find(Filters.eq(key, value)).first() != null;
    }

    public Document getDocument(String key, String value){
        return collection.find(Filters.eq(key, value)).first();
    }

    public void deleteDocument(String key, String value){
        collection.deleteOne(Filters.eq(key, value));
    }
}
