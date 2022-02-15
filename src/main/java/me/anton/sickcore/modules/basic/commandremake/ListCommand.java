package me.anton.sickcore.modules.basic.commandremake;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("list|glist")
public class ListCommand extends BaseCommand {

    @Default
    @Description("Opens the server gui")
    public void onServer(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        IBukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isHigher(Rank.MODERATOR)){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOMOD);
            return;
        }

        openServerInv(player);
    }

    private void openServerInv(IBukkitPlayer bukkitPlayer){
        HeadDatabaseAPI api = HeadDBAPI.getApi();
        PagedInventoryBuilder builder = new PagedInventoryBuilder(bukkitPlayer.api(), "§6List");

        for (ICloudService service : CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects()){
            if (service.isProxy())continue;
            ItemStack itemStack = null;
            if (service.getName().startsWith("Lobby"))itemStack = api.getItemHead("30032");
            if (service.getName().startsWith("Build"))itemStack = api.getItemHead("157");
            if (service.getName().startsWith("Survival"))itemStack = api.getItemHead("50099");
            if (itemStack == null)itemStack = api.getItemHead("8767");
            builder.addItem(new ItemBuilder(itemStack).setName("§6" + service.getName()).setLore("§7Players: §6" + service.getOnlineCount(),"§7Group: §6" + service.getGroupName(), "§7Click to connect to this server!").build(), service.getOnlineCount(), event -> {
                bukkitPlayer.api().cloud().cloudAPI().connect(service);
                DefaultSounds.levelUP.play(bukkitPlayer);
                bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§7You were teleported to the server " + service.getName() + "!", "§7Du wurdest zum Server " + service.getName() + " teleportiert!"));
            });
        }

        builder.resort();
        builder.open();
    }

}
