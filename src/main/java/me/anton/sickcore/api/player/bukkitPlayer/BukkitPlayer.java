package me.anton.sickcore.api.player.bukkitPlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.bukkitPlayer.messages.CommandMessages;
import me.anton.sickcore.games.defaults.all.vanish.VanishAction;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.SoundBuilder;
import me.anton.sickcore.api.utils.minecraft.player.uniqueid.UUIDFetcher;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        return player.getNickRank().getPrefix() + player.getNickRank().getColor() + getName();
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
}
