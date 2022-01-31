package me.anton.sickcore.api.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.anton.sickcore.api.utils.common.FileUtils;
import org.bson.Document;

import java.util.logging.Logger;

@Getter
public final class MongoConnection {

    private final MongoDatabase database;

    public MongoConnection() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(java.util.logging.Level.SEVERE);
        String uri = "mongodb://" + getOutput("username") + ":" + getOutput("password") + "@" + getOutput("address") + ":" + getOutput("port") + "/?authSource=" + getOutput("database");
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase((String) getOutput("database"));
    }

    private Object getOutput(String key){
        Document object = FileUtils.getAsDocument("mongo");
        return object.get(key);
    }

}