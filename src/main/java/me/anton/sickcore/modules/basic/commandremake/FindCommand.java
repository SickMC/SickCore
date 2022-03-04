package me.anton.sickcore.modules.basic.commandremake;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.dbassett.skullcreator.SkullCreator;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Rank;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("find")
public class FindCommand extends BaseCommand {

    @Default
    @CommandCompletion("@Players")
    @Syntax("<Player>")
    @Description("Find on which server the player is")
    public void onFind(CommandSender sender, String targetRaw){
        if (!(sender instanceof Player)) {
            ConsoleMessages.noPlayer(sender);
            return;
        }

        BukkitPlayer player = new BukkitPlayer(sender);

        if (!player.api().isHigher(Rank.MODERATOR)){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOMOD);
            return;
        }

        ICloudPlayer cloudTarget = CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(targetRaw).getBlocking();
        if (!cloudTarget.isOnline()){player.sendMessage(LanguagePath.NETWORK_COMMAND_NOPLAYER);return;}

        openFindInv(player, cloudTarget);
    }

    private void openFindInv(BukkitPlayer bukkitPlayer, ICloudPlayer player){
        InventoryBuilder builder = new InventoryBuilder(bukkitPlayer, "§6Find", 3);

        builder.setItem(new ItemBuilder(SkullCreator.itemFromUuid(player.getUniqueId()), bukkitPlayer).setName("§6" + player.getName()).setLore("§7Server: §6" + player.getConnectedServerName() ,"§7Click to connect to this server!"), 13, event -> {
            bukkitPlayer.api().cloud().cloudAPI().connect(player.getConnectedServer());
            DefaultSounds.levelUP.play(bukkitPlayer);
        });

        builder.fillEmpty();
        builder.open();
    }

}
