package me.anton.sickcore.core;

import de.dytanic.cloudnet.driver.CloudNetDriver;
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
    private final DatabaseModel languageModel;
    //private final DatabaseModel punishmentModel;
    private final DatabaseModel config;
    public final GlobalModuleHandler globalModuleHandler;
    private final Environment environment;

    public Core(){
        instance = this;
        mongoConnection = new MongoConnection();
        this.playerModel = new DatabaseModel("sickplayer");
        this.languageModel = new DatabaseModel("language");
        //this.punishmentModel = new DatabaseModel("punishment");
        this.config = new DatabaseModel("config");
        this.globalModuleHandler = new GlobalModuleHandler();
        if (CloudNetDriver.getInstance().getComponentName().startsWith("Proxy-"))this.environment = Environment.VELOCITY;
        else this.environment = Environment.BUKKIT;
    }

    public BukkitCore bukkit(){
        return (BukkitCore) instance;
    }

    public ProxyCore bungee(){ return (ProxyCore) instance; }

}
