package me.anton.sickcore.bootstrap;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import me.anton.sickcore.core.ProxyCore;
import org.slf4j.Logger;

@Getter
@Plugin(id = "sickcore", name = "SickCore", version = "1.0.0-SNAPSHOT", description = "Core for SickNetwork", authors = "Anton")
public class VelocityBootstrap {

    @Getter
    private final ProxyServer server;
    @Getter
    private final Logger logger;
    private ProxyCore core;

    @Inject
    public VelocityBootstrap(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyLoad(ProxyInitializeEvent event){
        core = new ProxyCore(this);
        core.onLoad();
    }

    @Subscribe
    public void onProxyUnLoad(ProxyShutdownEvent event){
        core.onUnLoad();
    }
}
