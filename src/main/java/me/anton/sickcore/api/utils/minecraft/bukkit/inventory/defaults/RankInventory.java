package me.anton.sickcore.api.utils.minecraft.bukkit.inventory.defaults;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.games.defaults.all.nick.AutoNickInventory;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryUsage;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.plugin.java.JavaPlugin;

public class RankInventory {

    private static String pickRank = "§7Click to choose your nickrank";
    private static String depickRank = "§7Klicke um deinen NickRank auszuwählen";

    public static void openNickAdminRankInv(IAPIPlayer apiplayer, String title, JavaPlugin plugin){
        IBukkitPlayer player = new BukkitPlayer(apiplayer);

        InventoryBuilder admin = new InventoryBuilder(apiplayer, title + "§6 - Admin", 27, InventoryUsage.UTILITY);

        admin.setItem(new ItemBuilder(Material.GRAY_DYE).setName(player.api().languageString("§6Player", "§6Spieler")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 10, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.PLAYER);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.PINK_DYE).setName(player.api().languageString("§6VIP", "§6VIP")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 11, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.VIP);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE).setName(player.api().languageString("§6MVP", "§6MVP")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 12, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.MVP);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.BLUE_DYE).setName(player.api().languageString("§6Staff", "§6Staff")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 13, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.CONTENT);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.GREEN_DYE).setName(player.api().languageString("§6Moderator", "§6Moderator")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 14, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.MODERATOR);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.RED_DYE).setName(player.api().languageString("§6Developer", "§6Developer")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 15, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.DEV);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.setItem(new ItemBuilder(Material.RED_DYE).setName(player.api().languageString("§6Admin", "§6Admin")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 16, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.ADMIN);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        admin.fillEmpty();

        admin.open();
    }


    public static void openNickRankInv(IAPIPlayer apiplayer, String title, JavaPlugin plugin){
        IBukkitPlayer player = new BukkitPlayer(apiplayer);

        InventoryBuilder builder = new InventoryBuilder(apiplayer, title, 27, InventoryUsage.UTILITY);

        builder.setItem(new ItemBuilder(Material.GRAY_DYE).setName(player.api().languageString("§6Player", "§6Spielers")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 11, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.PLAYER);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        builder.setItem(new ItemBuilder(Material.PINK_DYE).setName(player.api().languageString("§6VIP", "§6VIP")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 13, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.VIP);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE).setName(player.api().languageString("§6MVP", "§6MVP")).setLore(player.api().languageString(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(), 15, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank(Rank.MVP);
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player, plugin);
        });
        builder.fillEmpty();

        builder.open();
    }

}
