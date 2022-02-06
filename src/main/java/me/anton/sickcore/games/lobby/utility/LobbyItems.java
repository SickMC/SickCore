package me.anton.sickcore.games.lobby.utility;

import dev.dbassett.skullcreator.SkullCreator;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LobbyItems {

    public static void addLobbyItems(IBukkitPlayer player){
        Inventory inventory = player.getPlayer().getInventory();
        inventory.clear();
        //ProfileHead
        ItemStack profileHead = new ItemBuilder(SkullCreator.itemFromUuid(player.api().getUUID())).setName((String) player.api().languageObject("§6Profile", "§6Profil"))
                .setLore(player.api().languageString(LanguagePath.PAPER_LOBBY_LOBBYITEMS_PROFILE_DESCRIPTION).build()).build();

        player.getPlayer().getInventory().setItem(4, profileHead);
    }

}
