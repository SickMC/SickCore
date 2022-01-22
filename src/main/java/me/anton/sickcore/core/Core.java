package me.anton.sickcore.core;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseConfig;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.MongoConnection;

@Getter
public abstract class Core {

    @Getter
    protected static Core instance;
    private final MongoConnection mongoConnection;
    private final DatabaseModel playerModel;

    public Core(){
        instance = this;
        mongoConnection = new MongoConnection(new DatabaseConfig("localhost", 27017, "sickapi", "sickapi", "sickmc", "sickapi"));
        this.playerModel = new DatabaseModel("sickplayer");
    }

    public BukkitCore bukkit(){
        return (BukkitCore) instance;
    }

    public BungeeCore bungee(){ return (BungeeCore) instance; }

}
