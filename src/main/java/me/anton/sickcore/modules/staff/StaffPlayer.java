package me.anton.sickcore.modules.staff;

import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.modules.rank.Rank;
import me.anton.sickcore.modules.staff.notify.NotifyType;
import org.bson.Document;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class StaffPlayer implements IAPIPlayer {

    private final Document document;
    private final APIPlayer handle;

    public StaffPlayer(APIPlayer player){
        this.handle = player;
        this.document = FileUtils.getSubDocument("members", Staff.getConfig().getDocument()).get(player.getUUID().toString(), Document.class);
    }

    public boolean isNull(){
        return document == null;
    }

    public void update(Document document){
        Document first = FileUtils.getSubDocument("members" ,Staff.getConfig().getDocument());
        first.replace(handle.getUniqueID().toString(), document);
        Document second = Staff.getConfig().getDocument();
        second.replace("members", first);
        Staff.getConfig().update(second);
    }

    public Document getNotifyConfig(){
        return document.get("notify", Document.class);
    }

    public void setInActive(TimeUnit unit, int value){
        Document document = this.document;
        document.replace("cooldown", unit.toMillis(value));
        document.replace("active", false);
        List<String> membersList = document.getList("memberList", String.class);
        membersList.remove(handle.getUniqueID().toString());
        document.replace("memberList", membersList);
        for (NotifyType type : NotifyType.values()) {
            getHandle().setNotify(type, false);
        }
        handle.setRank(new Rank(handle.getDocument().getString("permanentRank")), TimeUnit.DAYS, 30);
        update(document);
    }

    public void setActive(Rank rank){
        Document document = this.document;
        if (System.currentTimeMillis() < document.getLong("cooldown"))return;
        document.replace("cooldown", 0);
        document.replace("active", true);
        List<String> membersList = document.getList("memberList", String.class);
        membersList.add(handle.getUniqueID().toString());
        document.replace("memberList", membersList);
        handle.setRank(rank);
        update(document);
    }

    @Override
    public UUID getUniqueID() {
        return handle.getUUID();
    }
}
