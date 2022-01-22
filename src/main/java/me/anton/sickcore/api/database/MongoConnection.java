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

    public MongoConnection(DatabaseConfig config) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(java.util.logging.Level.SEVERE);
        String uri = "mongodb://" + config.getUsername() + ":" + config.getPassword() + "@" + config.getAddress() + ":" + config.getPort() + "/?authSource=" + config.getAuthBase();
        if(config.getUsername().isEmpty() && config.getPassword().isEmpty()){
            uri = "mongodb://" + config.getAddress() + ":" + config.getPort() + "/?authSource=" + config.getAuthBase();
        }
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase(config.getDatabase());
    }

}