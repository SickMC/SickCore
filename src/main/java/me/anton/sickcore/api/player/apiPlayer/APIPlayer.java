package me.anton.sickcore.api.player.apiPlayer;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.*;
import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.player.cloudPlayer.CloudAPIPlayer;
import me.anton.sickcore.api.utils.common.StringUtils;
import me.anton.sickcore.api.utils.minecraft.player.UUIDFetcher;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.Environment;
import me.anton.sickcore.modules.staff.Staff;
import me.anton.sickcore.modules.staff.StaffPlayer;
import me.anton.sickcore.modules.staff.notify.NotifyType;
import me.anton.sickcore.modules.rank.Rank;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
public class APIPlayer implements IAPIPlayer {

    private final DatabaseModel model;
    private UUID uuid;
    private final Document document;

    public APIPlayer(@NotNull UUID uuid){
        this.uuid = uuid;
        this.model = Core.getInstance().getPlayerModel();
        this.document = model.getDocument("uuid", uuid.toString());
        if (!model.documentExists(new Finder("uuid", uuid.toString())) && Core.getInstance().getEnvironment().equals(Environment.VELOCITY))createAPIPlayer();
        if(!document.getString("name").equals(UUIDFetcher.fetchName(uuid))){
            document.replace("name",UUIDFetcher.fetchName(uuid));
            update();
        }
        if (document.getString("rank") == null){
            document.append("rank", "player").append("rankExpire", "none").append("permanentRank", "Player");
            update();
        }
        if (!document.getString("rankExpire").equals("none") && Long.parseLong(document.getString("rankExpire")) < System.currentTimeMillis())setRank(new Rank("Player"));
    }

    public APIPlayer(@NotNull String discordID){
        this.model = Core.getInstance().getPlayerModel();
        if (!model.documentExists(Finder.stringFinder("discordid", discordID))){
            this.document = null;
            return;
        }
        this.document = model.getDocument("discordid", discordID);
        this.uuid = UUID.fromString((String) model.get("uuid", "discordid", discordID));
    }

    private void createAPIPlayer() {
        if (!Core.getInstance().getEnvironment().equals(Environment.VELOCITY))return;
        model.createDocument(new Document("uuid", uuid.toString())
                .append("language", "deutschde")
                .append("nickname", UUIDFetcher.fetchName(uuid))
                .append("nickrank", "Player")
                .append("nickskin", "4NI0N")
                .append("autonick", "true")
                .append("discordid", "0")
                .append("rankcolor", "default")
                .append("name", UUIDFetcher.fetchName(uuid))
                .append("rank", "Player")
                .append("rankExpire", "none"))
                .append("permanentRank", "Player");
    }
   
    public Language getLanguage() {
        return Language.valueOf(document.getString("language").toUpperCase());
    }

    public void setLanguage(Language language) {
        document.replace("language", StringUtils.getEnumName(language).toLowerCase());
        update();
    }
   
    public void setNickname(String nickname) {
        document.replace("nickname", nickname);
        update();
    }

   
    public String getNickname() {
        return document.getString("nickname");
    }
   
    public void setNickRank(String rank) {
        document.replace("nickrank", rank);
        update();
    }

    public Rank getNickRank() {
        return new Rank(document.getString("nickrank"));
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

    public Rank getRank(){
        return new Rank(document.getString("rank"));
    }

    public void setRank(Rank rank, TimeUnit unit, int value) {
        document.replace("rank", rank.getName());
        document.replace("rankExpire", String.valueOf( System.currentTimeMillis() + unit.toMillis(value)));
        if (Core.getInstance().getEnvironment().equals(Environment.BUKKIT)) BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        update();
    }

    public void setRank(Rank rank) {
        document.replace("rank", rank.getName());
        document.replace("rankExpire", "none");
        if (Core.getInstance().getEnvironment().equals(Environment.BUKKIT)) BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        update();
    }

    public String getRankColor() {
        if (document.getString("rankcolor").equals("default")) return getRank().getParent().getColor();
        return document.getString("rankcolor");
    }

    public void setRankColor(String color) {
        document.replace("rankcolor", color);
        update();
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
        return getRank().getParent().getColor() + getRank().getName() +  "§8 × §r" + getRankColor() + getName() + "§r §r";
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

    public boolean is(String rank){
        return getRank().equals(new Rank(rank));
    }

    public boolean isMVP(){
        return is("MVP");
    }

    public boolean isVIP(){
        return is("VIP");
    }

    public boolean isPlayer(){
        return is("Player");
    }

    public boolean isTeam(){
        return new Staff().getStaffList().contains(new StaffPlayer(this)) || isAdmin();
    }

    public boolean isMod(){
        return getRank().getParent().getName().equals("Moderation") || isAdmin();
    }
   
    public boolean isAdmin() {
        return getRank().getParent().getName().equals("Administration");
    }

    public boolean isMaster(){
        return getRank().getParent().getName().equals("Master") || isAdmin();
    }

    public boolean isOnline(){
        return CloudAPI.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class).getOnlinePlayer(uuid) == null;
    }

    public void setNotify(NotifyType type, boolean value){
        Document updated = new StaffPlayer(this).getNotifyConfig();
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


