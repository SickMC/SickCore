package me.anton.sickcore.modules.motd;

import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.proxiedModule.ProxiedIModule;
import org.bson.Document;

@Getter
public class MOTDModule implements ProxiedIModule {

    @Getter
    private static MOTDModule instance;
    public Document document;

    @Override
    public void load() {
        instance = this;
        this.document = Core.getInstance().getAppereanceModel().getDocument(Finder.stringFinder("type", "motd"));

        register();
    }

    @Override
    public void unload() {

    }

    @Override
    public void register() {
        BungeeCore.getInstance().getProvider().register(new MOTDHandler());
    }

}
