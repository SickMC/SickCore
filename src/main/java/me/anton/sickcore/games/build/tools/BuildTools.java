package me.anton.sickcore.games.build.tools;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.games.build.tools.invisitemframes.InvisItemFrame;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("tools|t")
public class BuildTools extends BaseCommand {

    @Default
    public void onCMD(CommandSender sender){
        if (!(sender instanceof Player)){
            ConsoleMessages.noPlayer(sender);
            return;
        }

        BukkitPlayer player = new BukkitPlayer(sender);

        openToolsInventory(player);
    }

    private void openToolsInventory(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Buildtools");

        String click = "§7Click to get this item!";
        builder.addItem(new ItemBuilder(Material.DEBUG_STICK, player).setName("§6Debug Stick").setLore(click), event -> {
            Player bukkitPlayer = (Player) event.getWhoClicked();
            DefaultSounds.pling.play(player.getPlayer());
            bukkitPlayer.getInventory().addItem(new ItemStack(Material.DEBUG_STICK));
        });
        builder.addItem(new ItemBuilder(InvisItemFrame.generateInvisibleItemFrame(), player).setName("§6Invisible Item Frame").setLore(click), event -> {
            Player bukkitPlayer = (Player) event.getWhoClicked();
            DefaultSounds.pling.play(player.getPlayer());
            bukkitPlayer.getInventory().addItem(InvisItemFrame.generateInvisibleItemFrame());
        });
        builder.addItem(new ItemBuilder(Material.BARRIER, player).setName("§6Barrier").setLore(click), event -> {
            Player bukkitPlayer = (Player) event.getWhoClicked();
            DefaultSounds.pling.play(player.getPlayer());
            bukkitPlayer.getInventory().addItem(new ItemStack(Material.BARRIER));
        });
        builder.addItem(new ItemBuilder(Material.STRUCTURE_BLOCK, player).setName("§6Structure Block").setLore(click), event -> {
            Player bukkitPlayer = (Player) event.getWhoClicked();
            DefaultSounds.pling.play(player.getPlayer());
            bukkitPlayer.getInventory().addItem(new ItemStack(Material.STRUCTURE_BLOCK));
        });
        builder.addItem(new ItemBuilder(Material.STRUCTURE_VOID, player).setName("§6Structure Void").setLore(click),event -> {
            Player bukkitPlayer = (Player) event.getWhoClicked();
            DefaultSounds.pling.play(player.getPlayer());
            bukkitPlayer.getInventory().addItem(new ItemStack(Material.STRUCTURE_VOID));
        });

        builder.open();
    }

}
