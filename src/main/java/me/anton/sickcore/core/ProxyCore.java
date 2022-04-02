package me.anton.sickcore.core;

import com.velocitypowered.api.proxy.ProxyServer;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import lombok.Getter;
import me.anton.sickcore.bootstrap.VelocityBootstrap;
import me.anton.sickcore.core.module.globalmodule.GlobalModule;
import me.anton.sickcore.core.module.proxiedModule.ProxiedModuleHandler;
import me.anton.sickcore.modules.service.AutoRestart;


@Getter
public class ProxyCore extends Core{

    @Getter
    private static ProxyCore instance;
    private final ProxyServer plugin;
    private final ProxiedModuleHandler moduleHandler;
    private boolean isMainProxy;
    @Getter
    private final VelocityBootstrap bootstrap;

    public ProxyCore(VelocityBootstrap bootstrap){
        instance = this;
        this.plugin = bootstrap.getServer();
        this.bootstrap = bootstrap;
        moduleHandler = new ProxiedModuleHandler();
        moduleHandler.loadModules();
    }

    public void onLoad(){
        register();
        isMainProxy = CloudNetDriver.getInstance().getComponentName().equals("Proxy-1");

        Core.getInstance().getGlobalModuleHandler().getModules().forEach(GlobalModule::load);
        moduleHandler.loadModules();
    }

    public void onUnLoad(){
        Core.getInstance().getGlobalModuleHandler().getModules().forEach(GlobalModule::unload);
        moduleHandler.unLoadModules();
    }

    private void register(){
        new AutoRestart();
    }


}
