package me.anton.sickcore.games.lobby.utility.profile;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryUsage;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.games.defaults.all.HeadDBAPI;
import me.anton.sickcore.games.defaults.all.nick.AutoNickInventory;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class ProfileInventory{

    private static HeadDatabaseAPI headAPI = HeadDBAPI.getApi();

    public static void openProfileInventory(IBukkitPlayer player){
        IAPIPlayer iapiPlayer = player.api();
        InventoryBuilder builder = new InventoryBuilder(iapiPlayer, iapiPlayer.languageString("§6Profile", "§6Profil"), 27, InventoryUsage.UTILITY);

        if (iapiPlayer.getLanguage() == Language.DEUTSCH)builder.setItem(new ItemBuilder(headAPI.getItemHead("522")).setName("§6Sprache").setLore("§7Deutsch").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(),11, event -> openLanguageInventory(player));
        if (iapiPlayer.getLanguage() == Language.ENGLISCH)builder.setItem(new ItemBuilder(headAPI.getItemHead("890")).setName("§6Language").setLore("§7English").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build(),11, event -> openLanguageInventory(player));

        if (iapiPlayer.getRank() == Rank.PLAYER || iapiPlayer.getRank() == Rank.VIP)builder.setItem(new ItemBuilder(Material.NAME_TAG).setName("§6Nicktool").setLore("§7Available with MVP or higher").build(), 13, event -> DefaultSounds.anvil.play(player));
        else builder.setItem(new ItemBuilder(Material.NAME_TAG).setName("§6Nicktool").setLore("§7Nick: §7" + iapiPlayer.getNickname()).build(), 13, event -> AutoNickInventory.openAutoNickInventory(player));

        if (iapiPlayer.getRank() == Rank.PLAYER)builder.setItem(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLUE).setName("§6Rankcolor").setLore("§7Available with VIP or higher").addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE).build(), 15, event -> DefaultSounds.anvil.play(player));
        else builder.setItem(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLUE).setName("§6Rankcolor").setLore(iapiPlayer.languageString("§7Click to choose your rankcolor", "§7Klicke um deine Rankcolor festzulegen")).addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE).build(),15, event -> {openRankColorInventory(player);});

        builder.fillEmpty();
        builder.open();
    }

    public static void openLanguageInventory(IBukkitPlayer player){
        IAPIPlayer iapiPlayer = player.api();
        InventoryBuilder builder = new InventoryBuilder(iapiPlayer , iapiPlayer.languageString("§6Language", "§6Sprache"), 27, InventoryUsage.UTILITY);

        //Deutsch
        builder.setItem(new ItemBuilder(headAPI.getItemHead("522")).setName("§6Deutsch").setLore(iapiPlayer.languageString("§7Click to choose language!", "§7Klicke um diese Sprache auzuwählen!")).build(),12, event -> {
            iapiPlayer.setLanguage(Language.DEUTSCH);
            player.sendMessage("§7Your language is now §6Deutsch§7!", "§7Deine Sprache ist nun §6Deutsch§7!");
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
        });

        //Englisch
        builder.setItem(new ItemBuilder(headAPI.getItemHead("890")).setName("§6English").setLore(iapiPlayer.languageString("§7Click to choose language!", "§7Klicke um diese Sprache auzuwählen!")).build(),14, event -> {
            iapiPlayer.setLanguage(Language.ENGLISCH);
            player.sendMessage("§7Your language is now §6English§7!", "§7Deine Sprache ist nun §6Englisch§7!");
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
        });

        builder.fillEmpty();
        builder.open();
    }

    public static void openRankColorInventory(IBukkitPlayer player){
        IAPIPlayer iapiPlayer = player.api();
        InventoryBuilder builder = new InventoryBuilder(iapiPlayer, "§6Rankcolor", 54, InventoryUsage.UTILITY);

        builder.setItem(new ItemBuilder(Material.CLOCK).setName(iapiPlayer.languageString("§6Reset Rankcolor", "§6Setze Rankcolor zurück")).setLore(iapiPlayer.languageString("§7Click to reset your rankcolor to default!","§7Klicke um deine Rankcolor zum Standard zurückzusetzen!")).build(), 13, event -> {
            iapiPlayer.setRankColor(iapiPlayer.getDefaultRankColor());
            DefaultSounds.levelUP.play(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
            openProfileInventory(player);
        });

        String enClick = "§7Click to choose this color!";
        String deClick = "§7Klicke um diese Farbe zu wählen!";
        String lanClick = iapiPlayer.languageString(enClick, deClick);

        builder.setItem(new ItemBuilder(Material.GREEN_DYE).setName(iapiPlayer.languageString("§6Dark Green", "§6Dunkelgrün")).setLore(lanClick).build(), 28, event -> {
            iapiPlayer.setRankColor(ChatColor.DARK_GREEN);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.BLACK_DYE).setName(iapiPlayer.languageString("§6Black", "§6Schwarz")).setLore(lanClick).build(), 29, event -> {
            iapiPlayer.setRankColor(ChatColor.BLACK);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_BLUE_DYE).setName(iapiPlayer.languageString("§6Blue", "§6Blue")).setLore(lanClick).build(), 30, event -> {
            iapiPlayer.setRankColor(ChatColor.BLUE);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.WHITE_DYE).setName(iapiPlayer.languageString("§6White", "§6Weiß")).setLore(lanClick).build(), 31, event -> {
            iapiPlayer.setRankColor(ChatColor.WHITE);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.PURPLE_DYE).setName(iapiPlayer.languageString("§6Purple", "§6Lila")).setLore(lanClick).build(), 32, event -> {
            iapiPlayer.setRankColor(ChatColor.DARK_PURPLE);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.LIME_DYE).setName(iapiPlayer.languageString("§6Light Green", "§6Hellgrün")).setLore(lanClick).build(), 33, event -> {
            iapiPlayer.setRankColor(ChatColor.GREEN);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.PINK_DYE).setName(iapiPlayer.languageString("§6Pink", "§6Pink")).setLore(lanClick).build(), 34, event -> {
            iapiPlayer.setRankColor(ChatColor.LIGHT_PURPLE);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.ORANGE_DYE).setName(iapiPlayer.languageString("§6Gold", "§6Gold")).setLore(lanClick).build(), 37, event -> {
            iapiPlayer.setRankColor(ChatColor.GOLD);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.YELLOW_DYE).setName(iapiPlayer.languageString("§6Yellow", "§6Gelb")).setLore(lanClick).build(), 38, event -> {
            iapiPlayer.setRankColor(ChatColor.YELLOW);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.CYAN_DYE).setName(iapiPlayer.languageString("§6Cyan", "§6Türkis")).setLore(lanClick).build(), 39, event -> {
            iapiPlayer.setRankColor(ChatColor.DARK_AQUA);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.LIGHT_GRAY_DYE).setName(iapiPlayer.languageString("§6Gray", "§6Grau")).setLore(lanClick).build(), 40, event -> {
            iapiPlayer.setRankColor(ChatColor.GRAY);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.GRAY_DYE).setName(iapiPlayer.languageString("§6Dark Gray", "§6Dunkelgrau")).setLore(lanClick).build(), 41, event -> {
            iapiPlayer.setRankColor(ChatColor.DARK_GRAY);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.BLUE_DYE).setName(iapiPlayer.languageString("§6Dark Blue", "§6Dunkelblau")).setLore(lanClick).build(), 42, event -> {
            iapiPlayer.setRankColor(ChatColor.DARK_BLUE);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE).setName(iapiPlayer.languageString("§6Red", "§6Rot")).setLore(lanClick).build(), 43, event -> {
            iapiPlayer.setRankColor(ChatColor.RED);
            DefaultSounds.levelUP.play(player);
            openProfileInventory(player);
            BukkitCore.getInstance().getCurrentGame().getTablistBuilder().assignTeams();
        });

        builder.fillEmpty();
        builder.open();
    }

}
