package me.anton.sickcore.api.player.apiPlayer;

import eu.thesimplecloud.module.permission.player.IPermissionPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.player.cloudPlayer.ICloudAPIPlayer;
import org.bukkit.ChatColor;

import java.util.UUID;

public interface IAPIPlayer {

    public Language getLanguage();

    public void setLanguage(Language language);

    public Integer getCoins();

    public void setCoins(int amount);

    public void addCoins(int amount);

    public void removeCoins(int amount);

    public void setNickname(String nickname);

    public String getNickname();

    public void setNickRank(Rank rank);

    public Rank getNickRank();

    public String getNickSkinName();

    public void setNickSkinName(String nickSkinName);

    public boolean hasAutoNick();

    public void setAutoNick(boolean value);

    public String getDiscordID();

    public void setDiscordID(String discordID);

    public String getName();

    public boolean isVerified();

    public ChatColor getRankColor();

    public void setRankColor(ChatColor color);

    public boolean hasRankColor();

    public Rank getRank();

    public ChatColor getDefaultRankColor();

    public UUID getUUID();

    public String getDisplayName();

    public LanguageObject languageString(LanguagePath path);

    public Object languageObject(Object en, Object de);

    public IBukkitPlayer bukkit();

    public ICloudAPIPlayer cloud();

    public IPermissionPlayer perm();

    public IBungeePlayer bungee();

    public boolean isTeam();

    public boolean isAdmin();

    public boolean isHigher(Rank rank);

    public boolean isLower(Rank rank);
}
