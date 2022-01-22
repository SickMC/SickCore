package me.anton.sickcore.games.defaults.all.nick;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryUsage;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.defaults.RankInventory;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoNickInventory {

    public static void openAutoNickInventory(IBukkitPlayer player, JavaPlugin plugin){
        InventoryBuilder autoNickInventory = new InventoryBuilder(player.api(), player.api().languageString("§6AutoNick" , "§6AutoNick"), 27, InventoryUsage.UTILITY);

        //Nickname
        autoNickInventory.setItem(new ItemBuilder(Material.ANVIL).setName(player.api().languageString("§6Nickname", "§6Nickname")).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setLore(Arrays.asList("§7Nickname: " + player.api().getNickname(), player.api().languageString("§7Click to pick your nickname", "§7Klicke um deinen Nicknamen zu wählen"))).build(), 10, event -> {
            new AnvilGUI.Builder().onComplete((anvilPlayer, text) ->{
                        if (text.length() > 16) {
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.sendMessage("§4Your nickname has to be shorter than 17 symbols!", "§4Dein Nickname muss kürzer als 17 Zeichen sein!");
                            return AnvilGUI.Response.close();
                        }
                        if(text.contains(" ")){
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.sendMessage("§4Your nickname can not contain any special character!", "§4Dein Nickname darf keine Sonderzeichen enthalten!");
                            return AnvilGUI.Response.close();
                        }
                        String string = text;
                        player.api().setNickname(string);
                        DefaultSounds.levelUP.play(player.getPlayer());
                        return AnvilGUI.Response.close();
                    })
                    .text("Nickname")
                    .plugin(plugin)
                    .title(player.api().languageString("§6Set your nickname", "§6Setze deinen Nickname"))
                    .open(player.getPlayer());
        });

        //Nickskin
        ItemStack head = SkullCreator.itemFromName(player.api().getNickSkinName());
        ItemMeta meta = head.getItemMeta();
        meta.displayName(Component.text(player.api().languageString("§6Nickskin", "§6Nickskin")));
        List<Component> lores = new ArrayList<>();
        lores.add(Component.text("§7Nickskin: " + player.api().getNickSkinName()));
        lores.add(Component.text(player.api().languageString("§7Click to pick your nickskin", "§7Klicke um deinen Nickskin auszuwählen")));
        meta.lore(lores);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        head.setItemMeta(meta);

        autoNickInventory.setItem(new ItemBuilder(head).build(), 11, event -> {
            new AnvilGUI.Builder().onComplete((anvilPlayer, text) ->{
                        if (text.length() > 16) {
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.sendMessage("§4Your nickskin has to be shorter than 17 symbols!", "§4Dein Nickskin muss kürzer als 17 Zeichen sein!");
                            return AnvilGUI.Response.close();
                        }
                        if(text.contains(" ")){
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.sendMessage("§4Your nickskin can not contain any special character!", "§4Dein Nickskin darf keine Sonderzeichen enthalten!");
                            return AnvilGUI.Response.close();
                        }
                        String string = text;
                        player.api().setNickSkinName(string);
                        DefaultSounds.levelUP.play(player.getPlayer());
                        return AnvilGUI.Response.close();
                    })
                    .text("Nickskin")
                    .plugin(plugin)
                    .title(player.api().languageString("§6Set your nickskin", "§6Setze deinen Nickskin"))
                    .open(player.getPlayer());
        });

        //NickRank
        autoNickInventory.setItem(new ItemBuilder(Material.DIAMOND_HORSE_ARMOR).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName(player.api().languageString("§6NickRank", "§6NickRank")).setLore("§7NickRank: " + player.api().getNickRank().getPrefix()).setLore(player.api().languageString("§7Click to pick your nickrank", "§7Klicke um deinen Nickrank auszuwählen")).build(), 12, event -> {
            player.getPlayer().closeInventory();
            if (player.api().isAdmin()) RankInventory.openNickAdminRankInv(player.api(), "§6AutoNick - NickRank", plugin);
            if (!player.api().isAdmin())RankInventory.openNickRankInv(player.api() ,"§6AutoNick - NickRank" , plugin);
        });

        //AutoNick
        boolean isAutoNick = player.api().hasAutoNick();
        ItemStack hasAutonick = new ItemBuilder(Material.REPEATER).addEnchantment(Enchantment.MENDING, 1).addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).setName("§6AutoNick").setLore(player.api().languageString("§7Click to turn autonick off", "§7Klicke um AutoNick auszumachen")).build();
        ItemStack hasNoAutonick = new ItemBuilder(Material.REPEATER).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName("§6AutoNick").setLore(player.api().languageString("§7Click to turn autonick an", "§7Klicke um AutoNick anzumachen")).build();
        if (isAutoNick) {
            autoNickInventory.setItem(hasAutonick, 13, event -> {
                player.api().setAutoNick(false);
                player.getPlayer().closeInventory();
                openAutoNickInventory(player, plugin);
                player.playSound(DefaultSounds.levelUP);
            });
        }else {
            autoNickInventory.setItem(hasNoAutonick, 13, event -> {
                player.api().setAutoNick(true);
                player.getPlayer().closeInventory();
                openAutoNickInventory(player, plugin);
                player.playSound(DefaultSounds.levelUP);
            });
        }


        //RandomNick
        autoNickInventory.setItem(new ItemBuilder(Material.LAPIS_LAZULI).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName(player.api().languageString("§6Random Nickprofile", "§6Zufälliges Nickprofil")).setLore(player.api().languageString("§7Click to generate a random nickprofile", "§7Klicke um ein zufälliges Nickprofil zu generieren")).build(), 15, event -> {
            player.playSound(DefaultSounds.anvil);
            player.sendMessage("§7This feature is still in work!", "§7Dieses Feature ist noch in Arbeit!");
        });

        //ConfirmNick
        autoNickInventory.setItem(new ItemBuilder(Material.GREEN_DYE).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName(player.api().languageString("§6Confirm nick", "§6Bestätige deinen Nick")).setLore(player.api().languageString("§7Click to confirm your nick", "§7Klicke um deinen Nick zu bestätigen")).build(), 16, event -> {
            player.getPlayer().closeInventory();
            player.nick();
            player.playSound(DefaultSounds.levelUP);
        });
        autoNickInventory.fillEmpty();
        autoNickInventory.open();
    }


}
