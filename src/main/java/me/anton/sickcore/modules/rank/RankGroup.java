package me.anton.sickcore.modules.rank;

import lombok.Getter;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.utils.common.FileUtils;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.core.Core;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RankGroup {

    private Document document;
    private final List<String> permissions;
    private final String name;
    private final List<Rank> ranks;
    private final String prefix;
    private final int priority;
    private final String color;
    private final String discordID;
    private final String privatePrefix;

    public RankGroup(String name){
        Document rankDoc = Core.getInstance().getConfig().getDocument("type", "rankgroups");
        Document rankGroups = rankDoc.get("groups", Document.class);
        this.document = rankGroups.get(name, Document.class);
        this.permissions = this.document.getList("permissions", String.class);
        this.name = name;
        this.ranks = new ArrayList<>();
        List<String> rankNames = this.document.getList("ranks", String.class);
        for (String rankName : rankNames) ranks.add(new Rank(rankName));
        this.prefix = this.document.getString("publicPrefix");
        this.privatePrefix = this.document.getString("privatePrefix");
        this.priority = this.document.getInteger("priority");
        this.color = this.document.getString("color");
        this.discordID = this.document.getString("discordRoleID");
    }

    public void addPermission(String name){
        this.permissions.add(name);
        this.document.replace("permissions", permissions);
        update();
    }

    public void removePermission(String name){
        this.permissions.remove(name);
        this.document.replace("permissions", permissions);
        update();
    }

    private void update(){
        Document groups = FileUtils.getSubDocument("groups", Core.getInstance().getConfig().getDocument(Finder.stringFinder("type", "rankgroups")));
        groups.replace(name, document);
        Document all = Core.getInstance().getConfig().getDocument(Finder.stringFinder("type", "rankgroups"));
        all.replace("groups", groups);
        Core.getInstance().getConfig().updateDocument(Finder.stringFinder("type", "rankgroups"), all);
    }

}
