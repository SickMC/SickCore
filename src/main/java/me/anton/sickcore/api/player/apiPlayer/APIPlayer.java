package me.anton.sickcore.api.player.apiPlayer;

import eu.thesimplecloud.module.permission.PermissionPool;
import eu.thesimplecloud.module.permission.player.IPermissionPlayer;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.enums.RankBridge;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.api.player.cloudPlayer.ICloudAPIPlayer;
import me.anton.sickcore.api.utils.common.string.EnumUtils;
import me.anton.sickcore.api.utils.minecraft.player.uniqueid.UUIDFetcher;
import me.anton.sickcore.core.Core;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class APIPlayer implements IAPIPlayer {

    private final DatabaseModel model;
    private UUID uuid;
    private final Document document;

    public APIPlayer(@NotNull UUID uuid){
        this.uuid = uuid;
        this.model = Core.getInstance().getPlayerModel();
        this.document = model.getDocument("uuid", uuid.toString());
        if (!model.documentExists("uuid", uuid.toString()))createAPIPlayer();
        if(!document.getString("name").equals(UUIDFetcher.fetchName(uuid))){document.replace("name",UUIDFetcher.fetchName(uuid)); update();}
    }

    public APIPlayer(@NotNull String discordID){
        this.model = Core.getInstance().getPlayerModel();
        this.document = model.getDocument("discordid", discordID);
        if (!model.documentExists("discordid", discordID))return;
        this.uuid = UUID.fromString(model.getString("uuid", "discordid", discordID));
    }

    private void createAPIPlayer() {
        model.createDocument(new Document("uuid", uuid.toString())
                .append("language", "deutsch")
                .append("coins", 0)
                .append("nickname", UUIDFetcher.fetchName(uuid))
                .append("nickrank", "Player")
                .append("nickskin", "4NI0N")
                .append("autonick", "true")
                .append("discordid", "0")
                .append("rankcolor", "default")
                .append("name", UUIDFetcher.fetchName(uuid)));
    }

    @Override
    public Language getLanguage() {
        return Language.valueOf(document.getString("language").toUpperCase());
    }


    @Override
    public void setLanguage(Language language) {
        document.replace("language", EnumUtils.toName(language).toLowerCase());
        update();
    }

    @Override
    public Integer getCoins() {
        return document.getInteger("coins");
    }

    @Override
    public void setCoins(int amount) {
        document.replace("coins", amount);
        update();
    }

    @Override
    public void addCoins(int amount) {
        document.replace("coins", getCoins() + amount);
        update();
    }

    @Override
    public void removeCoins(int amount) {
        document.replace("coins", Math.max(getCoins() - amount, 0));
        update();
    }

    @Override
    public void setNickname(String nickname) {
        document.replace("nickname", nickname);
        update();
    }

    @Override
    public String getNickname() {
        return document.getString("nickname");
    }

    @Override
    public void setNickRank(Rank rank) {
        document.replace("nickrank", EnumUtils.toName(rank));
        update();
    }

    @Override
    public Rank getNickRank() {
        return Rank.valueOf(document.getString("nickrank").toUpperCase());
    }

    @Override
    public String getNickSkinName() {
        return document.getString("nickskin");
    }

    @Override
    public void setNickSkinName(String nickSkinID) {
        document.replace("nickskin", nickSkinID);
        update();
    }

    @Override
    public boolean hasAutoNick() {
        return !document.getString("autonick").equals("false");
    }

    @Override
    public void setAutoNick(boolean value) {
        if (value)document.replace("autonick", "true");
        if (!value)document.replace("autonick", "false");
        update();
    }

    @Override
    public String getDiscordID() {
        return document.getString("discordid");
    }

    @Override
    public void setDiscordID(String discordID) {
        document.replace("discordid", discordID);
        update();
    }

    @Override
    public String getName() {
        return document.getString("name");
    }

    @Override
    public boolean isVerified() {
        return !document.getString("discordid").equals("0");
    }

    @Override
    public ChatColor getRankColor() {
        if (!hasRankColor()) return getDefaultRankColor();
        return ChatColor.valueOf(document.getString("rankcolor").toUpperCase());
    }

    @Override
    public void setRankColor(ChatColor rankColor) {
        document.replace("rankcolor", rankColor.name().toLowerCase());
        update();
    }

    @Override
    public boolean hasRankColor() {
        return !document.getString("rankcolor").equals("default");
    }

    @Override
    public Rank getRank() {
        return Rank.valueOf(perm().getHighestPermissionGroup().getName().toUpperCase());
    }

    @Override
    public ChatColor getDefaultRankColor() {
        return RankBridge.getColor(getRank());
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String languageString(String en, String de) {
        if (getLanguage().equals(Language.ENGLISCH))
            return en;
        else if (getLanguage().equals(Language.DEUTSCH))
            return de;
        else
            return null;
    }

    @Override
    public String getDisplayName() {
        String s = null;
        if (hasRankColor()) return getRank().getFullPrefix() + getRankColor() + getName() + "§r §r";
        return getRank().getFullPrefix() + getDefaultRankColor() + getName() + "§r §r";
    }

    @Override
    public IBukkitPlayer bukkit() {
        return new BukkitPlayer(this);
    }

    @Override
    public IBungeePlayer bungee() {
        return new BungeePlayer(this);
    }

    @Override
    public ICloudAPIPlayer cloud() {
        return new CloudAPIPlayer(this);
    }

    @Override
    public IPermissionPlayer perm() {
        return PermissionPool.getInstance().getPermissionPlayerManager().getPermissionPlayer(uuid).getBlocking();
    }


    @Override
    public boolean isTeam(){
        return getRank() == Rank.ADMIN ||getRank() == Rank.MODERATOR || getRank() == Rank.CONTENT || getRank() == Rank.MODERATOR || getRank() == Rank.BUILDER;
    }

    @Override
    public boolean isAdmin() {
        return getRank() == Rank.ADMIN;
    }

    private void update(){
        model.updateDocument("uuid", uuid.toString(), document);
    }

}


