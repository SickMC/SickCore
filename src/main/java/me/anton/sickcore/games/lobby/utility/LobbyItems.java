package me.anton.sickcore.games.lobby.utility;

import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.games.lobby.utility.profile.ProfileInventory;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyItems {

    public static void addLobbyItems(BukkitPlayer player){
        Inventory inventory = player.getPlayer().getInventory();
        inventory.clear();
        //ProfileHead
        ItemStack profileHead = new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(player.getUniqueID()).setName((String) player.api().languageObject("§6Profile", "§6Profil"))
                .setLore(player.api().languageString(LanguagePath.PAPER_LOBBY_LOBBYITEMS_PROFILE_DESCRIPTION).build()).setPlayerEvent(event -> ProfileInventory.openProfileInventory(player)).build();

        player.getPlayer().getInventory().setItem(4, profileHead);
    }

}
