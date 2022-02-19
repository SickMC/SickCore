package me.anton.sickcore.modules.discord.handlers.player;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.modules.discord.DiscordModule;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class DiscordGamePlayer {

    private final String id;
    private Document document;

    public DiscordGamePlayer(String id){
        this.id = id;
        identify();
    }

    private void identify(){
        this.document = DiscordModule.getInstance().getGamePlayer().getDocument(Finder.stringFinder("userID", id));
        if (!document.containsKey("cooldown")){
            document.append("cooldown", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
        }
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
