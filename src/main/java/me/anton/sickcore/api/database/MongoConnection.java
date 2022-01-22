package me.anton.sickcore.api.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

import java.util.logging.Logger;

@Getter
public final class MongoConnection {

    private final MongoDatabase database;

    public MongoConnection() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(java.util.logging.Level.SEVERE);
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        this.database = client.getDatabase("sickmc");
    }

}