package me.anton.sickcore.modules.staff;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.LanguagePath;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.modules.staff.managing.StaffManageInv;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("staff")
public class StaffCommand extends BaseCommand {

    @Default
    public void onStaff(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        BukkitPlayer player = new BukkitPlayer(sender);
        if (!player.api().isTeam()){
            player.sendMessage(LanguagePath.NETWORK_COMMAND_NOSTAFF);
            return;
        }

        openStaffInv(player);
    }

    public void openStaffInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Staff", 3);

        builder.setItem(new ItemBuilder(Material.GOLDEN_PICKAXE, player).setName("§6Manage Staff Members").setLore(player.api().isMaster() ? "§7Click to open Staff Member Manage Inventory" : "§7This actions are available with Master or higher!"), 13, event -> {
            if (!player.api().isMaster()){
                DefaultSounds.anvil.play(player);
                event.setCancelled(true);
            }else {
                DefaultSounds.pling.play(player);
                StaffManageInv.openStaffManageInv(player);
            }
        });

        builder.fillEmpty();
        builder.open();
    }


}
