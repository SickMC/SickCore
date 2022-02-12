package me.anton.sickcore.games.survival.heads;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("head")
public class HeadManageCommand extends BaseCommand {

    @Default
    @Description("Opens the headcollection manager")
    public void onCollection(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }
        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isHigher(Rank.MODERATOR)){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOMOD);
            return;
        }

        openHeadCollection(player);
    }

    private void openHeadCollection(IBukkitPlayer opener){
        HeadDatabaseAPI api = HeadDBAPI.getApi();

        PagedInventoryBuilder inventoryBuilder = new PagedInventoryBuilder(opener.api(), "§6Head Collection");
        for (MobHead value : MobHead.values()) {
            if (api.getItemHead(String.valueOf(value.getId())) == null) Logger.error(value.getHeadName());
            inventoryBuilder.addItem(new ItemBuilder(api.getItemHead(String.valueOf(value.getId()))).setName("§6" + value.getHeadName()).setLore("§7Click to get this head!").build(), event -> {
                DefaultSounds.levelUP.play(opener);
                opener.getPlayer().getInventory().addItem(new ItemBuilder(api.getItemHead(String.valueOf(value.getId()))).setName("§6" + value.getHeadName()).build());
            });
        }

        inventoryBuilder.resort();
        inventoryBuilder.open(1);
    }

}
