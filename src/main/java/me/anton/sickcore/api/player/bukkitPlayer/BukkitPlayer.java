package me.anton.sickcore.api.player.bukkitPlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.language.LanguageObject;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.utils.common.ColorUtils;
import me.anton.sickcore.games.all.vanish.VanishAction;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import me.anton.sickcore.modules.punishment.PunishmentMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.UUID;

public class BukkitPlayer implements IAPIPlayer{

    private final APIPlayer player;
    private final Player bukkitPlayer;

    public BukkitPlayer(APIPlayer apiPlayer){
        this.player = apiPlayer;
        this.bukkitPlayer = Bukkit.getPlayer(apiPlayer.getUUID());
    }

    public BukkitPlayer(UUID uuid){
        this.player = new APIPlayer(uuid);
        this.bukkitPlayer = Bukkit.getPlayer(uuid);
    }

    public BukkitPlayer(Player player){
        this.player = new APIPlayer(player.getUniqueId());
        this.bukkitPlayer = player;
    }

    public BukkitPlayer(CommandSender sender){
        if (!(sender instanceof Player)) throw new NullPointerException();
        this.bukkitPlayer = (Player) sender;
        this.player = new APIPlayer(bukkitPlayer.getUniqueId());
    }

    
    public void sendMessage(LanguagePath path) {
        getPlayer().sendMessage(this.player.languageString(path).build());
    }

    
    public void sendMessage(LanguageObject object){
        getPlayer().sendMessage(object.build());
    }

    
    public void nick() {
        if (isNicked())return;
        NickAPI.nick(bukkitPlayer, player.getNickname());
        NickAPI.setSkin(bukkitPlayer, player.getNickSkinName());
        NickAPI.refreshPlayer(bukkitPlayer);
    }

    
    public void unnick() {
        if (!isNicked())return;
        NickAPI.resetNick(bukkitPlayer);
        NickAPI.resetSkin(bukkitPlayer);
        NickAPI.refreshPlayer(bukkitPlayer);
    }

    
    public boolean isNicked() {
        return NickAPI.isNicked(bukkitPlayer);
    }

    
    public String getName() {
        return NickAPI.getName(bukkitPlayer);
    }

    
    public String getNickSkinName() {
        return player.getNickSkinName();
    }

    
    public String getNickDisplayName() {
        return player.getNickRank().getParent().getColor() + player.getNickRank().getName() + "§8 × §r" + player.getNickRank().getParent().getColor() + api().getNickname() + "§r §r";
    }

    
    public APIPlayer api() {
        return player;
    }

    
    public Player getPlayer() {
        return bukkitPlayer;
    }

    
    public void sendTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder) {
        if (player.getLanguage().equals(Language.DEUTSCHDE))
            deTitleBuilder.sendTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCHUK))
            EnTitleBuilder.sendTitle(bukkitPlayer);
    }

    
    public void sendAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder) {
        if (player.getLanguage().equals(Language.DEUTSCHDE))
            deTitleBuilder.sendAnimatedTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCHUK))
            EnTitleBuilder.sendAnimatedTitle(bukkitPlayer);
    }

    
    public void sendCustomAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder, int speed, SoundBuilder finishSound) {
        if (player.getLanguage().equals(Language.DEUTSCHDE))
            deTitleBuilder.sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
        else if (player.getLanguage().equals(Language.ENGLISCHUK))
            EnTitleBuilder.sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
    }

    
    public void stopAllTitles() {
        bukkitPlayer.resetTitle();
    }

    
    public void playSound(Sound sound, int pitch, int volume) {
        new SoundBuilder(sound, net.kyori.adventure.sound.Sound.Source.AMBIENT, pitch, volume).play(bukkitPlayer);
    }

    
    public void playSound(SoundBuilder sound){
        sound.play(bukkitPlayer);
    }

    
    public void kick(String reasonen, String reasonde) {
        getPlayer().kick(PunishmentMessages.paperKick(reasonen, reasonde, this));
    }

    
    public void vanish() {
        new VanishAction(bukkitPlayer);
    }

    
    public void unVanish() {
        VanishAction.unVanish(bukkitPlayer);
    }

    
    public UUID getUniqueID() {
        return player.getUniqueID();
    }
}
