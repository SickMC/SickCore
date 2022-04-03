package me.anton.sickcore.games.survival;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.bukkitPlayer.AbstractGamePlayer;
import me.anton.sickcore.games.survival.heads.MobHead;
import org.bson.Document;

import java.util.UUID;

@Getter
public class SurvivalGamePlayer extends AbstractGamePlayer {

    private DatabaseModel model = SurvivalGame.getInstance().getPlayerModel();
    private Document documentPlayer;

    public SurvivalGamePlayer(UUID uuid) {
        super(uuid);

        if (!model.documentExists(Finder.uuidFinder("uuid", uuid))) {
            this.documentPlayer = new Document(new Document("uuid", uuid.toString())
                    .append("heads", createHeadCollection()));
            model.createDocument(documentPlayer);
        }else
            this.documentPlayer = model.getDocument(Finder.uuidFinder("uuid", uuid));
    }

    private Document createHeadCollection(){
        Document document = new Document("type", "heads");
        for (MobHead value : MobHead.values()) {
            document.put(value.name(), false);
        }

        return document;
    }

    public Document getHeadCollection(){
        return FileUtils.getSubDocument("heads", documentPlayer);
    }

    public void completeHead(MobHead head){
        Document d = getHeadCollection();
        d.put(head.name(), true);
        documentPlayer.put("heads", d);
        model.updateDocument(Finder.uuidFinder("uuid", getUniqueID()), documentPlayer);
    }

    public boolean hasCompleted(MobHead head){
        return (getHeadCollection().getBoolean(head.name()));
    }
}
