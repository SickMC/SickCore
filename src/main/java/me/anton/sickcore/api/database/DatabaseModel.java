package me.anton.sickcore.api.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.anton.sickcore.core.Core;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Getter
public class DatabaseModel {

    MongoCollection<Document> collection;

    public DatabaseModel(String name){
        MongoDatabase database = Core.getInstance().getMongoConnection().getDatabase();
        if (!database.listCollectionNames().into(new ArrayList<>()).contains(name))database.createCollection(name);
        this.collection = database.getCollection(name);
    }

    public void createDocument(Document document){
        if (documentExists(document))return;
        collection.insertOne(document);
    }

    public void updateDocument(Finder finder, Document document){
        if (!documentExists(finder))createDocument(document);
        collection.replaceOne(finder.toBson(), document);
    }

    public String getString(String key, Finder finder){
        return collection.find(finder.toBson()).first().getString(key);
    }
    public String getString(String key, Document finder){
        return collection.find(finder).first().getString(key);
    }

    public int getInteger(String key, Finder finder){
        return collection.find(finder.toBson()).first().getInteger(key);
    }
    public int getInteger(String key, Document finder){
        return collection.find(finder).first().getInteger(key);
    }

    public double getDouble(String key, Finder finder){
        return collection.find(finder.toBson()).first().getDouble(key);
    }
    public double getDouble(String key, Document finder){
        return collection.find(finder).first().getDouble(key);
    }

    public Date getDate(String key, Finder finder){
        return collection.find(finder.toBson()).first().getDate(key);
    }

    public Date getDate(String key, Document finder){
        return collection.find(finder).first().getDate(key);
    }

    public boolean getBoolean(String key, Finder finder){
        return collection.find(finder.toBson()).first().getBoolean(key);
    }

    public boolean getBoolean(String key, Document finder){
        return collection.find(finder).first().getBoolean(key);
    }

    public long getLong(String key, Finder finder){
        return collection.find(finder.toBson()).first().getLong(key);
    }

    public long getLong(String key, Document finder){
        return collection.find(finder).first().getLong(key);
    }

    public boolean documentExists(Document document){
        return collection.find(document).first() != null;
    }

    public boolean documentExists(Finder finder){
        return collection.find(finder.toBson()).first() != null;
    }

    public Document getDocument(Finder finder){
        return collection.find(finder.toBson()).first();
    }

    public Document getDocumentByString(String key, String value){
        return collection.find(new Finder(key, value).toBson()).first();
    }
}
