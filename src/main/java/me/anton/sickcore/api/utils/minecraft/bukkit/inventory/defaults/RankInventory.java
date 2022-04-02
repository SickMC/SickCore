package me.anton.sickcore.api.utils.minecraft.bukkit.inventory.defaults;

import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.games.all.nick.AutoNickInventory;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.modules.rank.Rank;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class RankInventory {

    private static String pickRank = "§7Click to choose your nickrank";
    private static String depickRank = "§7Klicke um deinen NickRank auszuwählen";

    public static void openNickAdminRankInv(BukkitPlayer player, String title){
        InventoryBuilder admin = new InventoryBuilder(player, title + "§6 - Admin", 3);

        admin.setItem(new ItemBuilder(Material.GRAY_DYE, player).setName((String) player.api().languageObject("§6Player", "§6Spieler")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 10,event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Player");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.PINK_DYE, player).setName((String) player.api().languageObject("§6VIP", "§6VIP")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 11, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("VIP");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE, player).setName((String) player.api().languageObject("§6MVP", "§6MVP")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 12,event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("MVP");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.BLUE_DYE, player).setName((String) player.api().languageObject("§6Staff", "§6Staff")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 13, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Content");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName((String) player.api().languageObject("§6Moderator", "§6Moderator")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 14, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Moderator");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.RED_DYE, player).setName((String) player.api().languageObject("§6Developer", "§6Developer")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 15, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Developer");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.setItem(new ItemBuilder(Material.RED_DYE, player).setName((String) player.api().languageObject("§6Admin", "§6Admin")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 16, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Admin");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        admin.fillEmpty();

        admin.open();
    }


    public static void openNickRankInv(BukkitPlayer player, String title){

        InventoryBuilder builder = new InventoryBuilder(player, title, 3);

        builder.setItem(new ItemBuilder(Material.GRAY_DYE, player).setName((String) player.api().languageObject("§6Player", "§6Spielers")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 11, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("Player");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        builder.setItem(new ItemBuilder(Material.PINK_DYE, player).setName((String) player.api().languageObject("§6VIP", "§6VIP")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 13,event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("VIP");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE, player).setName((String) player.api().languageObject("§6MVP", "§6MVP")).setLore((String) player.api().languageObject(pickRank, depickRank)).addItemFlags(ItemFlag.HIDE_ATTRIBUTES), 15, event -> {
            player.getPlayer().closeInventory();
            player.api().setNickRank("MVP");
            player.playSound(DefaultSounds.levelUP);
            AutoNickInventory.openAutoNickInventory(player);
        });
        builder.fillEmpty();

        builder.open();
    }

}
