package me.anton.sickcore.api.player.bukkitPlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.enums.RankBridge;
import me.anton.sickcore.api.player.bukkitPlayer.messages.CommandMessages;
import me.anton.sickcore.games.defaults.all.vanish.VanishAction;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import me.anton.sickcore.api.utils.minecraft.player.uniqueid.UUIDFetcher;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.haoshoku.nick.api.NickAPI;

import java.util.UUID;

public class BukkitPlayer implements IBukkitPlayer{

    private final IAPIPlayer player;
    private final Player bukkitPlayer;

    public BukkitPlayer(IAPIPlayer apiPlayer){
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

    @Override
    public void sendMessage(String en, String de) {
        if (player.getLanguage().equals(Language.ENGLISCH))
            bukkitPlayer.sendMessage(Component.text(en));
        else if (player.getLanguage().equals(Language.DEUTSCH))
            bukkitPlayer.sendMessage(Component.text(de));
    }

    @Override
    public void sendMessage(Component en, Component de) {
        if (player.getLanguage().equals(Language.ENGLISCH))
            bukkitPlayer.sendMessage(en);
        else if (player.getLanguage().equals(Language.DEUTSCH))
            bukkitPlayer.sendMessage(de);
    }

    @Override
    public void nick() {
        NickAPI.nick(bukkitPlayer, player.getNickname());
        NickAPI.setSkin(bukkitPlayer, player.getNickSkinName());
        NickAPI.setUniqueId(bukkitPlayer, UUIDFetcher.fetchUniqueId(player.getNickname()));
        NickAPI.refreshPlayer(bukkitPlayer);
    }

    @Override
    public void unnick() {
        if (!isNicked())return;
        NickAPI.resetNick(bukkitPlayer);
        NickAPI.resetSkin(bukkitPlayer);
        NickAPI.resetUniqueId(bukkitPlayer);
        NickAPI.refreshPlayer(bukkitPlayer);
    }

    @Override
    public boolean isNicked() {
        return NickAPI.isNicked(bukkitPlayer);
    }

    @Override
    public String getName() {
        return NickAPI.getName(bukkitPlayer);
    }

    @Override
    public String getNickSkinName() {
        return player.getNickSkinName();
    }

    @Override
    public String getNickedPrefix() {
        String nickedPrefix = player.getNickRank().getFullPrefix() + RankBridge.getColor(player.getNickRank()) + getName();
        return nickedPrefix;
    }

    @Override
    public IAPIPlayer api() {
        return player;
    }

    @Override
    public CommandMessages message() {
        return new CommandMessages(this);
    }

    @Override
    public Player getPlayer() {
        return bukkitPlayer;
    }

    @Override
    public void sendTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            deTitleBuilder.sendTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            EnTitleBuilder.sendTitle(bukkitPlayer);
    }

    @Override
    public void sendTitle(String title, String deTitle, String subTitle, String deSubTitle, int fadeIn, int stay, int fadeOut) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            new TitleBuilder(deTitle, deSubTitle, fadeIn, stay, fadeOut).sendTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            new TitleBuilder(title, subTitle, fadeIn, stay, fadeOut).sendTitle(bukkitPlayer);
    }

    @Override
    public void sendAnimatedTitle(String title, String deTitle, String subTitle, String deSubTitle, int fadeIn, int stay, int fadeOut) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            new TitleBuilder(deTitle, deSubTitle, fadeIn, stay, fadeOut).sendAnimatedTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            new TitleBuilder(title, subTitle, fadeIn, stay, fadeOut).sendAnimatedTitle(bukkitPlayer);
    }

    @Override
    public void sendCustomAnimatedTitle(String title, String deTitle, String subTitle, String deSubTitle, int fadeIn, int stay, int fadeOut, int speed, SoundBuilder finishSound) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            new TitleBuilder(deTitle, deSubTitle, fadeIn, stay, fadeOut).sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            new TitleBuilder(title, subTitle, fadeIn, stay, fadeOut).sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
    }

    @Override
    public void sendAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            deTitleBuilder.sendAnimatedTitle(bukkitPlayer);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            EnTitleBuilder.sendAnimatedTitle(bukkitPlayer);
    }

    @Override
    public void sendCustomAnimatedTitle(TitleBuilder EnTitleBuilder, TitleBuilder deTitleBuilder, int speed, SoundBuilder finishSound) {
        if (player.getLanguage().equals(Language.DEUTSCH))
            deTitleBuilder.sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
        else if (player.getLanguage().equals(Language.ENGLISCH))
            EnTitleBuilder.sendCustomAnimatedTitle(bukkitPlayer, speed, finishSound);
    }

    @Override
    public void stopAllTitles() {
        bukkitPlayer.resetTitle();
    }

    @Override
    public void playSound(Sound sound, int pitch, int volume) {
        new SoundBuilder(sound, net.kyori.adventure.sound.Sound.Source.AMBIENT, pitch, volume).play(bukkitPlayer);
    }

    @Override
    public void playSound(SoundBuilder sound){
        sound.play(bukkitPlayer);
    }

    @Override
    public void vanish() {
        new VanishAction(bukkitPlayer);
    }

    @Override
    public void unVanish() {
        VanishAction.unVanish(bukkitPlayer);
    }

    @Override
    public Material getRankWoolItem(){
        Material material = null;
        if (player.getRankColor() == ChatColor.GRAY)return Material.LIGHT_GRAY_WOOL;
        if (player.getRankColor() == ChatColor.DARK_AQUA)return Material.CYAN_WOOL;
        if (player.getRankColor() == ChatColor.GOLD)return Material.ORANGE_WOOL;
        if (player.getRankColor() == ChatColor.LIGHT_PURPLE)return Material.PINK_WOOL;
        if (player.getRankColor() == ChatColor.GREEN)return Material.LIME_WOOL;
        if (player.getRankColor() == ChatColor.DARK_PURPLE)return Material.PURPLE_WOOL;
        if (player.getRankColor() == ChatColor.DARK_BLUE)return Material.BLUE_WOOL;
        if (player.getRankColor() == ChatColor.BLUE)return Material.LIGHT_BLUE_WOOL;
        if (player.getRankColor() == ChatColor.DARK_GREEN)return Material.GREEN_WOOL;
        if (player.getRankColor() == ChatColor.DARK_RED)return Material.RED_WOOL;
        if (player.getRankColor() == ChatColor.DARK_GRAY)return Material.GRAY_WOOL;
        if (player.getRankColor() == ChatColor.BLACK)return Material.BLACK_WOOL;
        if (player.getRankColor() == ChatColor.WHITE)return Material.WHITE_WOOL;
        if (player.getRankColor() == ChatColor.YELLOW)return Material.YELLOW_WOOL;
        else return null;
    }


    @Override
    public ChatColor getChatColorByWool(Material wool) {
        if (wool == Material.LIGHT_GRAY_WOOL)return ChatColor.GRAY;
        if (wool == Material.CYAN_WOOL)return ChatColor.DARK_AQUA;
        if (wool == Material.ORANGE_WOOL)return ChatColor.GOLD;
        if (wool == Material.PINK_WOOL)return ChatColor.LIGHT_PURPLE;
        if (wool == Material.LIME_WOOL)return ChatColor.GREEN;
        if (wool == Material.PURPLE_WOOL)return ChatColor.DARK_PURPLE;
        if (wool == Material.BLUE_WOOL)return ChatColor.DARK_BLUE;
        if (wool == Material.LIGHT_BLUE_WOOL)return ChatColor.BLUE;
        if (wool == Material.GREEN_WOOL)return ChatColor.DARK_GREEN;
        if (wool == Material.RED_WOOL)return ChatColor.DARK_RED;
        if (wool == Material.GRAY_WOOL)return ChatColor.DARK_GRAY;
        if (wool == Material.BLACK_WOOL)return ChatColor.BLACK;
        if (wool == Material.WHITE_WOOL)return ChatColor.WHITE;
        if (wool == Material.YELLOW_WOOL)return ChatColor.YELLOW;
        else return null;
    }
}
