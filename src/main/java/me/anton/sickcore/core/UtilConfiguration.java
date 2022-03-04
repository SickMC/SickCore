package me.anton.sickcore.core;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import org.bson.Document;

@Getter
public class UtilConfiguration {

    public Document config;
    public DatabaseModel model;
    private String name;
    private Finder finder;

    public UtilConfiguration(String name){
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
