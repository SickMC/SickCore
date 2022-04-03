package me.anton.sickcore.modules.staff;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import lombok.Getter;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.oldcore.module.globalmodule.ModuleConfiguration;
import me.anton.sickcore.modules.staff.notify.NotifyType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Staff {

    @Getter
    public static final ModuleConfiguration config = StaffModule.getInstance().getConfig();
    private final List<StaffPlayer> staffList;

    public Staff(){
        this.staffList = new ArrayList<>();
        config.getDocument().getList("memberList", String.class).forEach(s -> {
            UUID uuid = UUID.fromString(s);
            this.staffList.add(new StaffPlayer(new APIPlayer(uuid)));
        });
    }

    public void notify(NotifyType type, String message){
        for (StaffPlayer player : this.staffList) {
            ICloudPlayer clPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getUniqueID());
            if (clPlayer == null)continue;
            if(!clPlayer.isOnline())continue;
            if (!player.getNotifyConfig().getBoolean("active"))continue;
            if (!player.getNotifyConfig().getBoolean(type.name().toLowerCase()))continue;
            clPlayer.sendMessage(message);
        }
    }

    public void addMember(APIPlayer player){
        Document newMember = new Document("warns", 0).append("cooldown", 0).append("active", true).append("notify", new Document("service", false).append("report", false).append("punishment", false).append("teamchat", false));
        Document toAdd = config.getDocument();
        Document members = FileUtils.getSubDocument("members", toAdd);
        members.append(player.getUniqueID().toString(), newMember);
        toAdd.replace("members", members);
        List<String> membersList = toAdd.getList("memberList", String.class);
        membersList.add(player.getUniqueID().toString());
        toAdd.replace("memberList", membersList);
        config.update(toAdd);
    }
}
