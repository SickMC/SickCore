package me.anton.sickcore.modules.notify;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryUsage;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bson.Document;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("notify")
public class NotifyCommand extends BaseCommand {

    @Default
    public void onNotify(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }
        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isTeam()){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);
            return;
        }

        openNotifyInv(player);
    }

    private void openNotifyInv(IBukkitPlayer bukkitPlayer){
        InventoryBuilder inventoryBuilder = new InventoryBuilder(bukkitPlayer.api(), "§6Notify", 27, InventoryUsage.UTILITY);
        HeadDatabaseAPI api = HeadDBAPI.getApi();

        Document config = bukkitPlayer.api().getNotifyConfig();

        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("45399")).setName("§6Service Updates").setLore(config.getBoolean("service") ? "§7Click to disable service updates" : "§7Click to enable service updated").setEnchanted(config.getBoolean("service")).build(),10 ,event -> {
            bukkitPlayer.api().setNotify(NotifyType.SERVICE, !config.getBoolean("service"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("28606")).setName("§6Team Chat").setLore(config.getBoolean("service") ? "§7Click to disable team chat" : "§7Click to enable team chat").setEnchanted(config.getBoolean("teamchat")).build(),12 ,event -> {
            bukkitPlayer.api().setNotify(NotifyType.TEAMCHAT, !config.getBoolean("teamchat"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("26414")).setName("§6Reports").setLore(config.getBoolean("service") ? "§7Click to disable reports" : "§7Click to enable reports").setEnchanted(config.getBoolean("report")).build(),14 ,event -> {
            bukkitPlayer.api().setNotify(NotifyType.REPORT, !config.getBoolean("report"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("3229")).setName("§6Punishments").setLore(config.getBoolean("service") ? "§7Click to disable punishments" : "§7Click to enable punishments").setEnchanted(config.getBoolean("punishment")).build(),16 ,event -> {
            bukkitPlayer.api().setNotify(NotifyType.PUNISHMENT, !config.getBoolean("punishment"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });

        inventoryBuilder.open();
    }

}
