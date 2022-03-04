package me.anton.sickcore.games.all.nick;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.defaults.RankInventory;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.core.Core;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoNickInventory {

    public static void openAutoNickInventory(BukkitPlayer player){
        InventoryBuilder autoNickInventory = new InventoryBuilder(player, (String) player.api().languageObject("§6AutoNick" , "§6AutoNick"), 3);

        //Nickname
        autoNickInventory.setItem(new ItemBuilder(Material.ANVIL, player).setName((String) player.api().languageObject("§6Nickname", "§6Nickname")).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setLore(Arrays.asList("§7Nickname: " + player.api().getNickname(), (String) player.api().languageObject("§7Click to pick your nickname", "§7Klicke um deinen Nicknamen zu wählen"))), 10,event -> {
            new AnvilGUI.Builder().onComplete((anvilPlayer, text) ->{
                        if (text.length() > 16) {
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.getPlayer().sendMessage((String) player.api().languageObject("§4Your nickname has to be shorter than 17 symbols!", "§4Dein Nickname muss kürzer als 17 Zeichen sein!"));
                            return AnvilGUI.Response.close();
                        }
                        if(text.contains(" ")){
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.getPlayer().sendMessage((String) player.api().languageObject("§4Your nickname can not contain any special character!", "§4Dein Nickname darf keine Sonderzeichen enthalten!"));
                            return AnvilGUI.Response.close();
                        }
                        String string = text;
                        player.api().setNickname(string);
                        DefaultSounds.levelUP.play(player.getPlayer());
                        return AnvilGUI.Response.close();
                    })
                    .text("Nickname")
                    .plugin(Core.getInstance().bukkit().getPlugin())
                    .title((String) player.api().languageObject("§6Set your nickname", "§6Setze deinen Nickname"))
                    .open(player.getPlayer());
        });

        //Nickskin
        ItemStack head = SkullCreator.itemFromName(player.api().getNickSkinName());
        ItemMeta meta = head.getItemMeta();
        meta.displayName(Component.text((String) player.api().languageObject("§6Nickskin", "§6Nickskin")));
        List<Component> lores = new ArrayList<>();
        lores.add(Component.text("§7Nickskin: " + player.api().getNickSkinName()));
        lores.add(Component.text((String) player.api().languageObject("§7Click to pick your nickskin", "§7Klicke um deinen Nickskin auszuwählen")));
        meta.lore(lores);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        head.setItemMeta(meta);

        autoNickInventory.setItem(new ItemBuilder(head, player), 11, event -> {
            new AnvilGUI.Builder().onComplete((anvilPlayer, text) ->{
                        if (text.length() > 16) {
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.getPlayer().sendMessage((String) player.api().languageObject("§4Your nickskin has to be shorter than 17 symbols!", "§4Dein Nickskin muss kürzer als 17 Zeichen sein!"));
                            return AnvilGUI.Response.close();
                        }
                        if(text.contains(" ")){
                            DefaultSounds.anvil.play(player.getPlayer());
                            player.getPlayer().sendMessage((String) player.api().languageObject("§4Your nickskin can not contain any special character!", "§4Dein Nickskin darf keine Sonderzeichen enthalten!"));
                            return AnvilGUI.Response.close();
                        }
                        String string = text;
                        player.api().setNickSkinName(string);
                        DefaultSounds.levelUP.play(player.getPlayer());
                        return AnvilGUI.Response.close();
                    })
                    .text("Nickskin")
                    .plugin(Core.getInstance().bukkit().getPlugin())
                    .title((String) player.api().languageObject("§6Set your nickskin", "§6Setze deinen Nickskin"))
                    .open(player.getPlayer());
        });

        //NickRank
        autoNickInventory.setItem(new ItemBuilder(Material.DIAMOND_HORSE_ARMOR, player).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName((String) player.api().languageObject("§6NickRank", "§6NickRank"))
                .setLore("§7NickRank: §6" + player.api().getNickRank().getName()).setLore((String) player.api().languageObject("§7Click to pick your nickrank", "§7Klicke um deinen Nickrank auszuwählen")), 12, event -> {
            player.getPlayer().closeInventory();
            if (player.api().isAdmin()) RankInventory.openNickAdminRankInv(player, "§6AutoNick - NickRank");
            if (!player.api().isAdmin())RankInventory.openNickRankInv(player ,"§6AutoNick - NickRank");
        });

        //AutoNick
        boolean isAutoNick = player.api().hasAutoNick();
        ItemBuilder hasAutonick = new ItemBuilder(Material.REPEATER, player).setEnchanted(true).setName("§6AutoNick").setLore((String) player.api().languageObject("§7Click to turn autonick off", "§7Klicke um AutoNick auszumachen"));
        ItemBuilder hasNoAutonick = new ItemBuilder(Material.REPEATER, player).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName("§6AutoNick").setLore((String) player.api().languageObject("§7Click to turn autonick an", "§7Klicke um AutoNick anzumachen"));
        if (isAutoNick)autoNickInventory.setItem(hasAutonick, 16, event -> {
            player.api().setAutoNick(false);
            player.getPlayer().closeInventory();
            openAutoNickInventory(player);
            player.playSound(DefaultSounds.levelUP);
        });
        else autoNickInventory.setItem(hasAutonick, 16, event -> {
            player.api().setAutoNick(true);
            player.getPlayer().closeInventory();
            openAutoNickInventory(player);
            player.playSound(DefaultSounds.levelUP);
        });

        //RandomNick
        autoNickInventory.setItem(new ItemBuilder(Material.LAPIS_LAZULI, player).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName((String) player.api().languageObject("§6Random Nickprofile", "§6Zufälliges Nickprofil")).setLore((String) player.api().languageObject("§7Click to generate a random nickprofile", "§7Klicke um ein zufälliges Nickprofil zu generieren")), 15, event -> {
            player.playSound(DefaultSounds.anvil);
            player.getPlayer().sendMessage((String) player.api().languageObject("§7This feature is still in work!", "§7Dieses Feature ist noch in Arbeit!"));
        });

        //ConfirmNick
        autoNickInventory.setItem(new ItemBuilder(Material.GREEN_DYE, player).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).setName((String) player.api().languageObject("§6Confirm nick", "§6Bestätige deinen Nick")).setLore((String) player.api().languageObject("§7Click to confirm your nick", "§7Klicke um deinen Nick zu bestätigen")), 16, event -> {
            player.getPlayer().closeInventory();
            player.nick();
            player.playSound(DefaultSounds.levelUP);
        });
        autoNickInventory.fillEmpty();
        autoNickInventory.open();
    }


}
