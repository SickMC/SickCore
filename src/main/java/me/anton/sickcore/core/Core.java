package me.anton.sickcore.core;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.MongoConnection;
import me.anton.sickcore.core.game.GameHandler;
import me.anton.sickcore.core.game.IGame;

@Getter
public abstract class Core {

    @Getter
    protected static Core instance;
    private final MongoConnection mongoConnection;
    private final DatabaseModel playerModel;
    private final DatabaseModel appereanceModel;

    public Core(){
        instance = this;
        mongoConnection = new MongoConnection();
        this.playerModel = new DatabaseModel("sickplayer");
        this.appereanceModel = new DatabaseModel("appereance");
    }

    public BukkitCore bukkit(){
        return (BukkitCore) instance;
    }

    public BungeeCore bungee(){ return (BungeeCore) instance; }

}
