package me.anton.sickcore.modules.discord.handlers.player;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.modules.discord.DiscordModule;
import org.bson.Document;

public class DiscordGamePlayer {

    private final String id;
    private Document document;

    public DiscordGamePlayer(String id){
        this.id = id;
        identify();
    }

    private void identify(){
        this.document = DiscordModule.getInstance().getGamePlayer().getDocument(Finder.stringFinder("userID", id));
    }

    public Object readFromDoc(String key){
        return document.get(key);
    }

    public int getPoints(){
        return (int) readFromDoc("points");
    }

    public String getId() {
        return id;
    }
}
