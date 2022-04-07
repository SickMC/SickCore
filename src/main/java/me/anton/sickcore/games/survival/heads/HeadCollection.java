package me.anton.sickcore.games.survival.heads;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.survival.SurvivalGamePlayer;
import me.anton.sickcore.modules.rank.Rank;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("hc|headcollection")
public class HeadCollection extends BaseCommand {

    @Default
    @Description("Opens the headcollection overview")
    public void onCollection(CommandSender sender, @Optional String target){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        BukkitPlayer player = new BukkitPlayer(sender);

        if (target == null){
            openHeadCollection(player, player);
        }else {
            if (!player.api().getRank().equals(new Rank("VIP"))){
                player.sendMessage(LanguagePath.NETWORK_AVAILABLE_WITHVIPPORHIGHER);
                return;
            }
            OfflinePlayer offlinePlayer = Bukkit.getPlayerExact(target);
            if (offlinePlayer == null){
                player.sendMessage(LanguagePath.NETWORK_COMMAND_NOPLAYER);
            }else {
                BukkitPlayer targetAPI = new BukkitPlayer(offlinePlayer.getUniqueId());
                openHeadCollection(targetAPI, player);
            }
        }

    }

    private void openHeadCollection(BukkitPlayer target, BukkitPlayer opener){
        SurvivalGamePlayer gamePlayer = new SurvivalGamePlayer(target.api().getUUID());
        HeadDatabaseAPI api = HeadDBAPI.getApi();

        PagedInventoryBuilder inventoryBuilder = new PagedInventoryBuilder(opener, "§6Head Collection");
        for (MobHead value : MobHead.values()) {
            if (gamePlayer.hasCompleted(value)) {
                inventoryBuilder.addItem(new ItemBuilder(api.getItemHead(String.valueOf(value.getId())), opener).setName("§6" + value.getHeadName()).setLore("§7You already found this head!").setEnchanted(true), event -> {
                    event.setCancelled(true);
                    DefaultSounds.pling.play(opener);
                });
            } else {
                inventoryBuilder.addItem(new ItemBuilder(api.getItemHead("9992"), opener).setName("§6" + value.getHeadName()).setLore("§7You haven't found this head yet!").setEnchanted(false), event -> {
                    event.setCancelled(true);
                    DefaultSounds.pling.play(opener);
                });
            }
        }
        inventoryBuilder.open();
    }
}
