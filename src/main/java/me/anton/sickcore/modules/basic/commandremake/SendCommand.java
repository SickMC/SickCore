package me.anton.sickcore.modules.basic.commandremake;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.all.HeadDBAPI;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("send")
public class SendCommand extends BaseCommand {

    @Default
    @CommandCompletion("@Players")
    @Syntax("<Player>")
    @Description("Teleports the player to a specific server")
    public void onSend(CommandSender sender, String targetRaw){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }

        Player player = (Player) sender;
        BukkitPlayer bukkitPlayer = new BukkitPlayer(player);

        if (!bukkitPlayer.api().isAdmin()){
            bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOADMIN);
            return;
        }

        ICloudPlayer targetCloud = CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(targetRaw).getBlocking();
        if (!targetCloud.isOnline()){bukkitPlayer.sendMessage(LanguagePath.NETWORK_COMMAND_NOPLAYER);return;}

        openServerInv(bukkitPlayer, targetCloud);
    }

    private void openServerInv(BukkitPlayer bukkitPlayer, ICloudPlayer target){
        HeadDatabaseAPI api = HeadDBAPI.getApi();
        PagedInventoryBuilder builder = new PagedInventoryBuilder(bukkitPlayer, "§6Send");

        for (ICloudService service : CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects()){
            if (service.isProxy())continue;
            ItemStack itemStack = null;
            if (service.getName().startsWith("Lobby"))itemStack = api.getItemHead("30032");
            if (service.getName().startsWith("Build"))itemStack = api.getItemHead("157");
            if (service.getName().startsWith("Survival"))itemStack = api.getItemHead("50099");
            if (itemStack == null)itemStack = api.getItemHead("8767");
            builder.addItem(new ItemBuilder(itemStack, bukkitPlayer).setName("§6" + service.getName()).setLore("§7Players: §6" + service.getOnlineCount(), "§7Group: §6" + service.getGroupName() ,"§7Click to connect the player to this server!"), event -> {
                target.connect(service);
                DefaultSounds.levelUP.play(bukkitPlayer);
                bukkitPlayer.getPlayer().sendMessage((String) bukkitPlayer.api().languageObject("§7You were teleported to the server " + service.getName() + "!", "§7Du wurdest zum Server " + service.getName() + " teleportiert!"));
            });
        }
        builder.open();
    }


}
