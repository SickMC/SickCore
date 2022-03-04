package me.anton.sickcore.games.build.game;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.games.monopoly.street.MonopolyStreet;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("position")
public class PositionCommand extends BaseCommand {

    @Default
    @Description("Opens the position gui")
    public void onPos(CommandSender sender){
        if (!(sender instanceof Player)){
            return;
        }
        BukkitPlayer player = new BukkitPlayer(sender);

        openPosInv(player);
    }

    private void openPosInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Position", 3);

        builder.setItem(new ItemBuilder(Material.RED_MUSHROOM_BLOCK, player).setName("§6Monopoly").setLore("§7Click to use this gamemode"),13, event -> {
            openMonopolyInv(player);
        });

        builder.fillEmpty();
        builder.open();
    }

    private void openMonopolyInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Position - Monopoly");

        for (MonopolyStreet street : MonopolyStreet.values()){
            builder.addItem(new ItemBuilder(street.getIcon(), player).setName("§6" + street.getName()).setLore("§7Click to set position for this street!"), event -> {
                Document path = new DatabaseModel("Monopoly").getDocument(Finder.stringFinder("type", "positions"));
                Document worldPos;
                if (!path.containsKey(player.getPlayer().getWorld().getName())) {
                    Document streets = new Document("streets", "ok");
                    for (MonopolyStreet allstreets : MonopolyStreet.values()) {
                        streets.put(allstreets.name(), "0");
                    }
                    path.put(player.getPlayer().getWorld().getName(), streets);
                    worldPos = streets;
                } else
                    worldPos = path.get(player.getPlayer().getWorld().getName(), Document.class);
                Location location = player.getPlayer().getLocation();
                worldPos.put(street.name(), location.toString());
                path.put(player.getPlayer().getWorld().getName(), worldPos);
                new DatabaseModel("Monopoly").updateDocument(Finder.stringFinder("type", "positions"), path);
                DefaultSounds.levelUP.play(player);
            });
        }
        builder.open();
    }


}
