package me.anton.sickcore.games.lobby.utility.profile;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.Replacable;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.anton.sickcore.games.all.nick.AutoNickInventory;
import me.anton.sickcore.modules.rank.Rank;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class ProfileInventory{

    private static HeadDatabaseAPI headAPI = HeadDBAPI.getApi();

    public static void openProfileInventory(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, (String) player.api().languageObject("§6Profile", "§6Profil"), 3);

        ItemBuilder language = (ItemBuilder) player.api().languageObject(new ItemBuilder(headAPI.getItemHead("890"), player).setName("§6Language").setLore("§7English").addItemFlags(ItemFlag.HIDE_ATTRIBUTES), new ItemBuilder(headAPI.getItemHead("522"), player ).setName("§6Sprache").setLore("§7Deutsch").addItemFlags(ItemFlag.HIDE_ATTRIBUTES));

        builder.setItem(language, 11, event -> openLanguageInventory(player));

        if (!player.api().isMVP() || player.api().isTeam())builder.setItem(new ItemBuilder(Material.NAME_TAG, player).setName("§6Nicktool").setLore("§7" + player.api().languageString(LanguagePath.NETWORK_AVAILABLE_WITHMVPORHIGHER).build()), 13, event -> DefaultSounds.anvil.play(player));
        else builder.setItem(new ItemBuilder(Material.NAME_TAG, player).setName("§6Nicktool").setLore("§7Nick: §7" + player.api().getNickname()), 13, event -> AutoNickInventory.openAutoNickInventory(player));

        if (player.api().isPlayer())builder.setItem(new ItemBuilder(Material.LEATHER_CHESTPLATE, player).setLeatherArmorColor(Color.BLUE).setName("§6Rankcolor").setLore("§7" + player.api().languageString(LanguagePath.NETWORK_AVAILABLE_WITHVIPPORHIGHER).build()).addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE), 15, event -> DefaultSounds.anvil.play(player));
        else builder.setItem(new ItemBuilder(Material.LEATHER_CHESTPLATE, player).setLeatherArmorColor(Color.BLUE).setName("§6Rankcolor").setLore(player.api().languageString(LanguagePath.PAPER_COMMAND_LOBBY_PROFILEINVENTORY_CHOOSE).replace(new Replacable("%e%", "e"), new Replacable("%object%", "rankcolor"), new Replacable("%deobject%", "Rankcolor")).build()).addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE),15, event -> openRankColorInventory(player));

        builder.fillEmpty();
        builder.open();
    }

    public static void openLanguageInventory(BukkitPlayer player){
        APIPlayer iapiPlayer = player.api();
        InventoryBuilder builder = new InventoryBuilder(player, (String) iapiPlayer.languageObject("§6Language", "§6Sprache"), 3);

        //Deutsch
        builder.setItem(new ItemBuilder(headAPI.getItemHead("522"), player).setName("§6Deutsch").setLore(iapiPlayer.languageString(LanguagePath.PAPER_COMMAND_LOBBY_PROFILEINVENTORY_CHOOSE).replace(new Replacable("%e%", "e"), new Replacable("%object%", "language"), new Replacable("%deobject%", "Sprache")).build()),12, event -> {
            iapiPlayer.setLanguage(Language.DEUTSCHDE);
            player.getPlayer().sendMessage(player.api().languageString(LanguagePath.PAPER_COMMAND_LOBBY_RANKINVENTORY_LANGUAGE_PICK).replace(new Replacable("%language%", "Deutsch")).build());
            DefaultSounds.levelUP.play(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
            openProfileInventory(player);
        });

        //Englisch
        builder.setItem(new ItemBuilder(headAPI.getItemHead("890"), player).setName("§6English").setLore(iapiPlayer.languageString(LanguagePath.PAPER_COMMAND_LOBBY_PROFILEINVENTORY_CHOOSE).replace(new Replacable("%e%", "e"), new Replacable("%object%", "language"), new Replacable("%deobject%", "Sprache")).build()),14, event -> {
            iapiPlayer.setLanguage(Language.ENGLISCHUK);
            player.getPlayer().sendMessage(player.api().languageString(LanguagePath.PAPER_COMMAND_LOBBY_RANKINVENTORY_LANGUAGE_PICK).replace(new Replacable("%language%", "English")).build());
            DefaultSounds.levelUP.play(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
            openProfileInventory(player);
        });

        builder.fillEmpty();
        builder.open();
    }

    public static void openRankColorInventory(BukkitPlayer player){
        APIPlayer iapiPlayer = player.api();
        InventoryBuilder builder = new InventoryBuilder(player, "§6Rankcolor", 6);

        builder.setItem(new ItemBuilder(Material.CLOCK, player).setName(iapiPlayer.languageString(LanguagePath.PAPER_COMMAND_LOBBY_RANKINVENTORY_RESETRANKCOLOR).build()).setLore(iapiPlayer.languageString(LanguagePath.PAPER_COMMAND_LOBBY_RANKINVENTORY_RESETRANKCOLORDESCRIPTION).build()), 13, event -> {
            iapiPlayer.setRankColor(iapiPlayer.getRank().getParent().getColor());
            DefaultSounds.levelUP.play(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
            openProfileInventory(player);
        });

        String lanClick = iapiPlayer.languageString(LanguagePath.PAPER_COMMAND_LOBBY_RANKINVENTORY_PICKCOLOR).build();

        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName((String) iapiPlayer.languageObject("§6Dark Green", "§6Dunkelgrün")).setLore(lanClick), 28, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.DARK_GREEN.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.BLACK_DYE, player).setName((String) iapiPlayer.languageObject("§6Black", "§6Schwarz")).setLore(lanClick), 29, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.BLACK.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE, player).setName((String) iapiPlayer.languageObject("§6Blue", "§6Blau")).setLore(lanClick), 30, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.BLUE.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.WHITE_DYE, player).setName((String) iapiPlayer.languageObject("§6White", "§6Weiß")).setLore(lanClick), 31, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.WHITE.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.PURPLE_DYE, player).setName((String) iapiPlayer.languageObject("§6Purple", "§6Lila")).setLore(lanClick), 32, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.DARK_PURPLE.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.LIME_DYE, player).setName((String) iapiPlayer.languageObject("§6Light Green", "§6Hellgrün")).setLore(lanClick), 33, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.GREEN.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.PINK_DYE, player).setName((String) iapiPlayer.languageObject("§6Pink", "§6Pink")).setLore(lanClick), 34, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.LIGHT_PURPLE.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.ORANGE_DYE, player).setName((String) iapiPlayer.languageObject("§6Gold", "§6Gold")).setLore(lanClick), 37, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.GOLD.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.YELLOW_DYE, player).setName((String) iapiPlayer.languageObject("§6Yellow", "§6Gelb")).setLore(lanClick), 38, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.YELLOW.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.CYAN_DYE, player).setName((String) iapiPlayer.languageObject("§6Cyan", "§6Türkis")).setLore(lanClick), 39, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.DARK_AQUA.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_GRAY_DYE, player).setName((String) iapiPlayer.languageObject("§6Gray", "§6Grau")).setLore(lanClick), 40,event -> {
            iapiPlayer.setRankColor("§" + ChatColor.GRAY.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.GRAY_DYE, player).setName((String) iapiPlayer.languageObject("§6Dark Gray", "§6Dunkelgrau")).setLore(lanClick), 41, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.DARK_GRAY.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.BLUE_DYE, player).setName((String) iapiPlayer.languageObject("§6Dark Blue", "§6Dunkelblau")).setLore(lanClick), 42, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.DARK_BLUE.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName((String) iapiPlayer.languageObject("§6Red", "§6Rot")).setLore(lanClick), 43, event -> {
            iapiPlayer.setRankColor("§" + ChatColor.RED.getChar());
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablist().reload();
        });

        builder.fillEmpty();
        builder.open();
    }

}
