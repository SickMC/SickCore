package me.anton.sickcore.core.module.globalmodule;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.core.Core;
import org.bson.Document;

@Getter
public class ModuleConfiguration {

    private GlobalModule module;
    public Document config;
    public DatabaseModel model;
    private String name;
    private Finder finder;

    public ModuleConfiguration(GlobalModule module, String name){
        this.module = module;
        this.model = Core.getInstance().getConfig();
        this.name = name;
        this.finder = Finder.stringFinder("type", name);
    }

    public Document getDocument() {
        return model.getOrCreate(finder);
    }

    public void update(Document document){
        model.updateDocument(finder, document);
    }
}
