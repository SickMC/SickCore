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
public class Rank {

    private final String name;
    private final Document document;
    private final List<String> permissions;
    private final String discordRoleID;
    private final String privateDiscordRoleID;

    public Rank(String name){
        this.name = name;
        Document rankDoc = Core.getInstance().getConfig().getDocument("type", "ranks");
        Document rankGroups = FileUtils.getSubDocument("ranks", rankDoc);
        this.document = FileUtils.getSubDocument(name, rankGroups);
        if (document == null)Logger.debug("Rank Doc == null", getClass());
        this.permissions = document.getList("extraPermissions", String.class);
        this.discordRoleID = document.getString("discordRoleID");
        this.privateDiscordRoleID = document.getString("privateDiscordRoleID");
    }

    public void addPermission(String name){
        this.permissions.add(name);
        this.document.replace("extraPermissions", permissions);
        update();
    }

    public RankGroup getParent(){
        return new RankGroup(document.getString("parent"));
    }

    public void removePermission(String name){
        this.permissions.remove(name);
        this.document.replace("extraPermissions", permissions);
        update();
    }

    public List<String> getPermissions(){
        List<String> perms = new ArrayList<>();
        perms.addAll(this.permissions);
        perms.addAll(this.getParent().getPermissions());
        return perms;
    }

    private void update(){
        Document groups = FileUtils.getSubDocument("ranks", Core.getInstance().getConfig().getDocument(Finder.stringFinder("type", "ranks")));
        groups.replace(name, document);
        Document all = Core.getInstance().getConfig().getDocument(Finder.stringFinder("type", "ranks"));
        all.replace("ranks", groups);
        Core.getInstance().getConfig().updateDocument(Finder.stringFinder("type", "ranks"), all);
    }

}
