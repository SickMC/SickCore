package me.anton.sickcore.core;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.MongoConnection;

@Getter
public abstract class Core {

    @Getter
    protected static Core instance;
    private final MongoConnection mongoConnection;
    private final DatabaseModel playerModel;
    private final DatabaseModel appereanceModel;
    private final DatabaseModel languageModel;

    public Core(){
        instance = this;
        mongoConnection = new MongoConnection();
        this.playerModel = new DatabaseModel("sickplayer");
        this.appereanceModel = new DatabaseModel("appereance");
        this.languageModel = new DatabaseModel("language");
    }

    public boolean isProxy(){
        return CloudAPI.getInstance().getThisSidesName().startsWith("Proxy-");
    }

    public BukkitCore bukkit(){
        return (BukkitCore) instance;
    }

    public BungeeCore bungee(){ return (BungeeCore) instance; }

}
