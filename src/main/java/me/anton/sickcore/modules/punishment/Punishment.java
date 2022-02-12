package me.anton.sickcore.modules.punishment;

import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import org.bson.Document;

import java.util.UUID;

@Getter
public class Punishment {

    public Document document;

    public Punishment(UUID uuid, PunishmentType type, PunishReason reason, String proof, UUID executor){
        createModel(uuid, type, reason, proof, executor);
    }

    private void createModel(UUID uuid, PunishmentType type, PunishReason reason, String proof, UUID executor){
        if (type.equals(PunishmentType.BAN));


    }

    public Punishment(Document document){
        this.document = document;
    }

    public Punishment(String id){
        this.document = PunishmentModule.getInstance().getPunishments().getDocument(Finder.stringFinder("id", id));
    }

    public boolean isActive(){
        if (document.getLong("time") == 0)return true;
        if (document.getString("type").equals("kick"))return false;
        return !(System.currentTimeMillis() < System.currentTimeMillis() + document.getLong("time"));
    }

}
