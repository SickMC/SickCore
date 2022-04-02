package me.anton.sickcore.modules.staff.notify;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.anton.sickcore.modules.staff.StaffPlayer;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bson.Document;
import org.bukkit.Material;
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
        BukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isTeam()){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);
            return;
        }

        openNotifyInv(player);
    }

    private void openNotifyInv(BukkitPlayer bukkitPlayer){
        InventoryBuilder inventoryBuilder = new InventoryBuilder(bukkitPlayer, "§6Notify", 4);
        HeadDatabaseAPI api = HeadDBAPI.getApi();

        Document config = new StaffPlayer(bukkitPlayer.api()).getNotifyConfig();

        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("45399"), bukkitPlayer).setName("§6Service Updates").setLore(config.getBoolean("service") ? "§7Click to disable service updates" : "§7Click to enable service updated").setEnchanted(config.getBoolean("service")),10, event -> {
            bukkitPlayer.api().setNotify(NotifyType.SERVICE, !config.getBoolean("service"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem( new ItemBuilder(config.getBoolean("service") ? Material.LIME_DYE : Material.GRAY_DYE, bukkitPlayer).setName("§6Service Updates").setLore(config.getBoolean("service") ? "§7Click to disable service updates" : "§7Click to enable service updated"), 19, event -> {
            bukkitPlayer.api().setNotify(NotifyType.SERVICE, !config.getBoolean("service"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });


        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("28606"), bukkitPlayer).setName("§6Team Chat").setLore(config.getBoolean("teamchat") ? "§7Click to disable team chat" : "§7Click to enable team chat").setEnchanted(config.getBoolean("teamchat")),12, event -> {
            bukkitPlayer.api().setNotify(NotifyType.TEAMCHAT, !config.getBoolean("teamchat"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem( new ItemBuilder(config.getBoolean("teamchat") ? Material.LIME_DYE : Material.GRAY_DYE, bukkitPlayer).setName("§6Team Chat")
                .setLore(config.getBoolean("teamchat") ? "§7Click to disable teamchat" : "§7Click to enable teamchat"), 21, event -> {
            bukkitPlayer.api().setNotify(NotifyType.TEAMCHAT, !config.getBoolean("teamchat"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });

        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("26414"), bukkitPlayer).setName("§6Reports").setLore(config.getBoolean("report") ? "§7Click to disable reports" : "§7Click to enable reports").setEnchanted(config.getBoolean("report")),14, event -> {
            bukkitPlayer.api().setNotify(NotifyType.REPORT, !config.getBoolean("report"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem( new ItemBuilder(config.getBoolean("report") ? Material.LIME_DYE : Material.GRAY_DYE, bukkitPlayer).setName("§6Reports")
                .setLore(config.getBoolean("report") ? "§7Click to disable reports" : "§7Click to enable reports"), 23, event -> {
            bukkitPlayer.api().setNotify(NotifyType.REPORT, !config.getBoolean("report"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });

        inventoryBuilder.setItem(new ItemBuilder(api.getItemHead("3229"), bukkitPlayer).setName("§6Punishments").setLore(config.getBoolean("punishment") ? "§7Click to disable punishments" : "§7Click to enable punishments").setEnchanted(config.getBoolean("punishment")),16, event -> {
            bukkitPlayer.api().setNotify(NotifyType.PUNISHMENT, !config.getBoolean("punishment"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });
        inventoryBuilder.setItem( new ItemBuilder(config.getBoolean("punishment") ? Material.LIME_DYE : Material.GRAY_DYE, bukkitPlayer).setName("§6Punishments")
                .setLore(config.getBoolean("punishment") ? "§7Click to disable punishments" : "§7Click to enable punishments"), 25, event -> {
            bukkitPlayer.api().setNotify(NotifyType.PUNISHMENT, !config.getBoolean("punishment"));
            DefaultSounds.levelUP.play(bukkitPlayer);
            openNotifyInv(bukkitPlayer);
        });

        inventoryBuilder.fillEmpty();
        inventoryBuilder.open();
    }

}
