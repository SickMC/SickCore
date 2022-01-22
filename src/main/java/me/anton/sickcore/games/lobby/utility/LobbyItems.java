package me.anton.sickcore.games.lobby.utility;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyItems {

    public static void addLobbyItems(IBukkitPlayer player){
        Inventory inventory = player.getPlayer().getInventory();
        inventory.clear();
        //ProfileHead
        ItemStack profileHead = new ItemBuilder(SkullCreator.itemFromUuid(player.api().getUUID())).setName(player.api().languageString("§6Profile", "§6Profil"))
                .setLore(player.api().languageString("§7Click to view your profile overview!", "§7Klicke um deinen Profil Überblick zu sehen!")).build();

        player.getPlayer().getInventory().setItem(4, profileHead);
    }

}
