package me.anton.sickcore.api.player.apiPlayer;

import eu.thesimplecloud.module.permission.PermissionPool;
import eu.thesimplecloud.module.permission.player.IPermissionPlayer;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.api.utils.common.StringUtils;
import me.anton.sickcore.api.utils.minecraft.player.UUIDFetcher;
import me.anton.sickcore.core.BungeeCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.Environment;
import me.anton.sickcore.modules.notify.NotifyType;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class APIPlayer implements IAPIPlayer {

    private final DatabaseModel model;
    private UUID uuid;
    private final Document document;

    public APIPlayer(@NotNull UUID uuid){
        this.uuid = uuid;
        this.model = Core.getInstance().getPlayerModel();
        this.document = model.getDocument("uuid", uuid.toString());
        if (!model.documentExists(new Finder("uuid", uuid.toString())) && Core.getInstance().getEnvironment().equals(Environment.BUNGEECORD))createAPIPlayer();
        if(!document.getString("name").equals(UUIDFetcher.fetchName(uuid))){
            document.replace("name",UUIDFetcher.fetchName(uuid));
            update();
        }
        if (document.get("notify", Document.class) == null){
            document.append("notify", new Document("service", false).append("report", false).append("punishment", false).append("teamchat", false));
            update();
        }
    }

    public APIPlayer(@NotNull String discordID){
        this.model = Core.getInstance().getPlayerModel();
        this.document = model.getDocument("discordid", discordID);
        if (!model.documentExists(Finder.stringFinder("discordid", discordID)))return;
        this.uuid = UUID.fromString((String) model.get("uuid", "discordid", discordID));
    }

    private void createAPIPlayer() {
        if (!Core.getInstance().getEnvironment().equals(Environment.BUNGEECORD))return;
        if (!BungeeCore.getInstance().isMainProxy())return;
        model.createDocument(new Document("uuid", uuid.toString())
                .append("language", "deutschde")
                .append("coins", 0)
                .append("nickname", UUIDFetcher.fetchName(uuid))
                .append("nickrank", "Player")
                .append("nickskin", "4NI0N")
                .append("autonick", "true")
                .append("discordid", "0")
                .append("rankcolor", "default")
                .append("name", UUIDFetcher.fetchName(uuid))
                .append("notify", new Document("service", false).append("report", false).append("punishment", false).append("teamchat", false)));
    }

   
    public Language getLanguage() {
        return Language.valueOf(document.getString("language").toUpperCase());
    }


   
    public void setLanguage(Language language) {
        document.replace("language", StringUtils.getEnumName(language).toLowerCase());
        update();
    }

   
    public Integer getCoins() {
        return document.getInteger("coins");
    }

   
    public void setCoins(int amount) {
        document.replace("coins", amount);
        update();
    }

   
    public void addCoins(int amount) {
        document.replace("coins", getCoins() + amount);
        update();
    }

   
    public void removeCoins(int amount) {
        document.replace("coins", Math.max(getCoins() - amount, 0));
        update();
    }

   
    public void setNickname(String nickname) {
        document.replace("nickname", nickname);
        update();
    }

   
    public String getNickname() {
        return document.getString("nickname");
    }

   
    public void setNickRank(Rank rank) {
        document.replace("nickrank", StringUtils.getEnumName(rank));
        update();
    }

   
    public Rank getNickRank() {
        return Rank.valueOf(document.getString("nickrank").toUpperCase());
    }

   
    public String getNickSkinName() {
        return document.getString("nickskin");
    }

   
    public void setNickSkinName(String nickSkinID) {
        document.replace("nickskin", nickSkinID);
        update();
    }

   
    public boolean hasAutoNick() {
        return !document.getString("autonick").equals("false");
    }

   
    public void setAutoNick(boolean value) {
        if (value)document.replace("autonick", "true");
        if (!value)document.replace("autonick", "false");
        update();
    }

   
    public String getDiscordID() {
        return document.getString("discordid");
    }

   
    public void setDiscordID(String discordID) {
        document.replace("discordid", discordID);
        update();
    }

   
    public String getName() {
        return document.getString("name");
    }

   
    public boolean isVerified() {
        return !document.getString("discordid").equals("0");
    }

   
    public String getRankColor() {
        if (!hasRankColor()) return getDefaultRankColor();
        return document.getString("rankcolor");
    }

   
    public void setRankColor(String color) {
        document.replace("rankcolor", color);
        update();
    }

   
    public boolean hasRankColor() {
        return !document.getString("rankcolor").equals("default");
    }

   
    public Rank getRank() {
        return Rank.valueOf(perm().getHighestPermissionGroup().getName().toUpperCase());
    }

   
    public String getDefaultRankColor() {
        return getRank().getColor();
    }

   
    public UUID getUUID() {
        return uuid;
    }

   
    public LanguageObject languageString(LanguagePath path) {
        return new LanguageObject(this, path);
    }

   
    public Object languageObject(Object en, Object de) {
        if (getLanguage().equals(Language.ENGLISCHUK))
            return en;
        else if (getLanguage().equals(Language.DEUTSCHDE))
            return de;
        else
            return null;
    }

   
    public String getDisplayName() {
        if (hasRankColor()) return getRank().getColor() + getRank().getName() +  "§8 × §r" + getRankColor() + getName() + "§r §r";
        return getRank().getColor() + getRank().getName() +  "§8 × §r" + getDefaultRankColor() + getName() + "§r §r";
    }

   
    public BukkitPlayer bukkit() {
        return new BukkitPlayer(this);
    }

   
    public BungeePlayer bungee() {
        return new BungeePlayer(this);
    }

   
    public CloudAPIPlayer cloud() {
        return new CloudAPIPlayer(this);
    }

   
    public IPermissionPlayer perm() {
        return PermissionPool.getInstance().getPermissionPlayerManager().getPermissionPlayer(uuid).getBlocking();
    }

   
    public boolean isTeam(){
        return getRank() == Rank.ADMIN ||getRank() == Rank.MODERATOR || getRank() == Rank.CONTENT || getRank() == Rank.MODERATOR || getRank() == Rank.BUILDER || getRank() == Rank.DEV;
    }

   
    public boolean isAdmin() {
        return getRank() == Rank.ADMIN;
    }

   
    public boolean isHigher(Rank rank) {
        return getRank().getPriority() <= rank.getPriority();
    }

   
    public boolean isLower(Rank rank) {
        return getRank().getPriority() >= rank.getPriority();
    }

   
    public Document getNotifyConfig() {
        return document.get("notify", Document.class);
    }

   
    public void setNotify(NotifyType type, boolean value){
        Document updated = getNotifyConfig();
        updated.put(type.name().toLowerCase(), value);
        document.put("notify", updated);
        update();
    }

    private void update(){
        model.updateDocument("uuid", uuid.toString(), document);
    }

    @Override
    public UUID getUniqueID() {
        return uuid;
    }
}


