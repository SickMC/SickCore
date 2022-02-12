package me.anton.sickcore.core;

import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.MongoConnection;
import me.anton.sickcore.core.module.globalmodule.GlobalModuleHandler;

@Getter
public abstract class Core {

    @Getter
    protected static Core instance;
    private final MongoConnection mongoConnection;
    private final DatabaseModel playerModel;
    private final DatabaseModel appereanceModel;
    private final DatabaseModel languageModel;
    private final DatabaseModel punishmentModel;
    public final GlobalModuleHandler globalModuleHandler;
    private final Environment environment;

    public Core(){
        instance = this;
        mongoConnection = new MongoConnection();
        this.playerModel = new DatabaseModel("sickplayer");
        this.appereanceModel = new DatabaseModel("sicknetwork");
        this.languageModel = new DatabaseModel("language");
        this.punishmentModel = new DatabaseModel("punishment");
        this.globalModuleHandler = new GlobalModuleHandler();
        if (CloudAPI.getInstance().getThisSidesName().startsWith("Proxy-"))this.environment = Environment.BUNGEECORD;
        else this.environment = Environment.BUKKIT;
    }

    public BukkitCore bukkit(){
        return (BukkitCore) instance;
    }

    public BungeeCore bungee(){ return (BungeeCore) instance; }

}
