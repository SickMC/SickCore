package me.anton.sickcore.modules.staff.managing;

import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.common.FileUtils;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.InventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.inventory.PagedInventoryBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.item.ItemBuilder;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.sound.DefaultSounds;
import me.anton.sickcore.core.BukkitCore;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.modules.rank.Rank;
import me.anton.sickcore.modules.rank.RankGroup;
import me.anton.sickcore.modules.staff.Staff;
import me.anton.sickcore.modules.staff.StaffPlayer;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StaffManageInv {

    public static void openStaffManageInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Staff Manage", 3);

        builder.setItem(new ItemBuilder(Material.DIAMOND_SWORD, player).setName("§6Kick Staff Member").setLore("§7Click to start staff kick process!"), 11, event -> {
            DefaultSounds.pling.play(player);
            openFirstStaffKickInv(player);
        });

        builder.setItem(new ItemBuilder(Material.TORCH, player).setName("§6Add Staff Member").setLore("§7Click to start staff add process!"), 15, event -> {
            DefaultSounds.pling.play(player);
            openFirstStaffAddInv(player);
        });

        builder.setItem(new ItemBuilder(Material.LADDER, player).setName("§6Up/Downrank Member").setLore("§7Click to start up/downranking process"), 13, event -> {
            DefaultSounds.pling.play(player);
            openUpDownRankInv(player);
        });

        builder.fillEmpty();
        builder.open();
    }

    private static void openUpDownRankInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6RankChange", 3);

        builder.setItem(new ItemBuilder(Material.LANTERN, player).setName("§6Uprank").setLore("§7Click to start uprank process"), 11, event -> {
            openFirstUprankInv(player);
            DefaultSounds.pling.play(player);
        });

        builder.setItem(new ItemBuilder(Material.IRON_PICKAXE, player).setName("§6Change Rank").setLore("§7Click to start the RankChange process"), 13, event -> {
            openFirstDownrankInv(player);
            DefaultSounds.pling.play(player);
        });

        builder.setItem(new ItemBuilder(Material.SOUL_LANTERN, player).setName("§6Downrank").setLore("§7Click to start downrank process"), 15, event -> {
            openFirstRankChangeInv(player);
            DefaultSounds.pling.play(player);
        });

        builder.fillEmpty();
        builder.open();
    }

    private static void openFirstRankChangeInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Member");

        for (StaffPlayer staffPlayer : new Staff().getStaffList()) {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(staffPlayer.getUniqueID()).setName("§6" + staffPlayer.getHandle().getName()).setLore("§7Click to choose this player!"), event -> {
                DefaultSounds.pling.play(player);
                openSecondRankChangeInv(player, staffPlayer);
            });
        }
    }
    private static void openSecondRankChangeInv(BukkitPlayer player, StaffPlayer target){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Rank - " + toAdd.api().getName());

        Document rankConfig = FileUtils.getSubDocument("ranks", Core.getInstance().getConfig().getDocument("type", "ranks"));
        Set<String> rankNames = rankConfig.keySet();
        List<RankGroup> notToAdd = Arrays.asList(new RankGroup("Player"), new RankGroup("MVP"), new RankGroup("VIP"));
        for (String rankName : rankNames) {
            Rank rank = new Rank(rankName);
            Material material;
            if (notToAdd.contains(rank.getParent()))continue;
            if (rankName.startsWith("H-"))continue;
            if (rank.getParent().equals(new RankGroup("Administration")))material = Material.REDSTONE_TORCH;
            else if (rank.getParent().equals(new RankGroup("Moderation")))material = Material.IRON_SWORD;
            else if (rank.getParent().equals(new RankGroup("Builder")))material = Material.GRASS_BLOCK;
            else if (rank.getParent().equals(new RankGroup("Developer")))material = Material.IRON_BARS;
            else if (rank.getParent().equals(new RankGroup("Brain")))material = Material.REPEATER;
            else if (rank.getParent().equals(new RankGroup("Master")))material = Material.FISHING_ROD;
            else material = Material.STONE;

            builder.addItem(new ItemBuilder(material, player).setName("§6" + rankName).setLore("§7Click to choose this rank!"), event -> {
                rankToAdd = rank;
                DefaultSounds.pling.play(player);
                openThirdRankChangeInv(player, target, rank);
            });
        }

        builder.open();
    }
    private static void openThirdRankChangeInv(BukkitPlayer player, StaffPlayer target, Rank rank){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Rank Change - " + toAdd.getPlayer().getName(), 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(toAdd.getUniqueID()).setName("§6" + target.getHandle().getName()), 13);
        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName("§6Confirm").setLore("§7Click to confirm!"), 30, event -> {
            target.getHandle().setRank(rank);
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName("§6Cancel").setLore("§7Click to cancel!"), 32, event -> {
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });

        builder.fillEmpty();
        builder.open();
    }

    private static void openFirstDownrankInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Member");

        for (StaffPlayer staffPlayer : new Staff().getStaffList()) {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(staffPlayer.getUniqueID()).setName("§6" + staffPlayer.getHandle().getName()).setLore("§7Click to choose this player!"), event -> {
                DefaultSounds.pling.play(player);
                openSecondDownrankInv(player, staffPlayer);
            });
        }
    }
    private static void openSecondDownrankInv(BukkitPlayer player, StaffPlayer target){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Choose Rank", 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(target.getUniqueID()).setName("§6" + target.getHandle().getName()), 13);

        builder.fillEmpty();
        builder.fill(new ItemBuilder(Material.AIR, player).setName(null), 30,32);

        for (Rank rank : target.getHandle().getRank().getParent().getRanks()) {
            builder.addItem(new ItemBuilder(Material.DIAMOND_PICKAXE, player).setName("§6" + rank.getName()).setLore("§7Click to choose this rank"), event -> {
                openThirdDownrankInv(player, target, rank);
                DefaultSounds.pling.play(player);
            });
        }

        builder.open();
    }
    private static void openThirdDownrankInv(BukkitPlayer player, StaffPlayer target, Rank rank){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Member downrank - " + target.getHandle().getName(), 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(target.getUniqueID()).setName("§6" + target.getHandle().getName()), 13);
        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName("§6Confirm").setLore("§7Click to confirm!"), 30, event -> {
            target.getHandle().setRank(rank);
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName("§6Cancel").setLore("§7Click to cancel!"), 32, event -> {
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });

        builder.fillEmpty();
        builder.open();
    }

    private static void openFirstUprankInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Member");

        for (StaffPlayer staffPlayer : new Staff().getStaffList()) {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(staffPlayer.getUniqueID()).setName("§6" + staffPlayer.getHandle().getName()).setLore("§7Click to choose this player!"), event -> {
                DefaultSounds.pling.play(player);
                openSecondUprankInv(player, staffPlayer);
            });
        }
    }
    private static void openSecondUprankInv(BukkitPlayer player, StaffPlayer target){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Choose Rank", 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(target.getUniqueID()).setName("§6" + target.getHandle().getName()), 13);

        builder.fillEmpty();
        builder.fill(new ItemBuilder(Material.AIR, player).setName(null), 30,32);

        for (Rank rank : target.getHandle().getRank().getParent().getRanks()) {
            builder.addItem(new ItemBuilder(Material.DIAMOND_PICKAXE, player).setName("§6" + rank.getName()).setLore("§7Click to choose this rank"), event -> {
                openThirdRankInv(player, target, rank);
                DefaultSounds.pling.play(player);
            });
        }

        builder.open();
    }
    private static void openThirdRankInv(BukkitPlayer player, StaffPlayer target, Rank rank){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Member uprank - " + target.getHandle().getName(), 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(target.getUniqueID()).setName("§6" + target.getHandle().getName()), 13);
        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName("§6Confirm").setLore("§7Click to confirm!"), 30, event -> {
            target.getHandle().setRank(rank);
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
            if (Bukkit.getOnlinePlayers().contains(target.getHandle().bukkit().getPlayer())){
                Player targetPl = target.getHandle().bukkit().getPlayer();
                targetPl.getWorld().strikeLightningEffect(targetPl.getLocation());
                targetPl.getWorld().strikeLightningEffect(targetPl.getLocation());
                BukkitCore.getInstance().getCurrentGame().getTablist().reload();
            }
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName("§6Cancel").setLore("§7Click to cancel!"), 32, event -> {
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });

        builder.fillEmpty();
        builder.open();
    }

    private static StaffPlayer toKick;

    private static void openFirstStaffKickInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Member");

        for (StaffPlayer staffPlayer : new Staff().getStaffList()) {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(player.getUniqueID()).setName("§6" + player.getName()).setLore("§7Click to choose this player!"), event -> {
                toKick = staffPlayer;
                DefaultSounds.pling.play(player);
                openSecondStaffKickInv(player);
            });
        }

        builder.open();
    }
    private static void openSecondStaffKickInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Staff kick - " + toKick.getHandle().getName(), 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(toKick.getUniqueID()).setName("§6" + toKick.getHandle().getName()), 13, event -> event.setCancelled(true));
        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName("§6Confirm").setLore("§7Click to confirm!"), 30, event -> {
            new StaffRemoveAction(new StaffPlayer(player.api())).perform();
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName("§6Cancel").setLore("§7Click to cancel!"), 32, event -> {
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });

        builder.fillEmpty();
        builder.open();
    }

    private static BukkitPlayer toAdd;
    private static Rank rankToAdd;

    private static void openFirstStaffAddInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Player");

        for (Player onlinePlayer : player.getPlayer().getServer().getOnlinePlayers()) {
            builder.addItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(onlinePlayer.getUniqueId()).setName("§6" + onlinePlayer.getName()).setLore("§7Click to choose this player!"), event -> {
                BukkitPlayer bPlayer = new BukkitPlayer(onlinePlayer);
                toAdd = bPlayer;
                DefaultSounds.pling.play(player);
                openSecondStaffAddInv(player);
            });
        }

        builder.open();
    }
    private static void openSecondStaffAddInv(BukkitPlayer player){
        PagedInventoryBuilder builder = new PagedInventoryBuilder(player, "§6Choose Rank - " + toAdd.api().getName());

        Document rankDoc = Core.getInstance().getConfig().getDocument(Finder.stringFinder("type", "ranks"));
        Document rankConfig = rankDoc.get("ranks", Document.class);
        List<String> notToAdd = Arrays.asList("Player", "MVP","VIP");
        for (String rankName : rankConfig.keySet()) {
            Rank rank = new Rank(rankName);
            Material material;
            if (notToAdd.contains(rank.getParent().getName()))continue;
            if (rank.getParent().equals(new RankGroup("Administration")))material = Material.REDSTONE_TORCH;
            else if (rank.getParent().equals(new RankGroup("Moderation")))material = Material.IRON_SWORD;
            else if (rank.getParent().equals(new RankGroup("Builder")))material = Material.GRASS_BLOCK;
            else if (rank.getParent().equals(new RankGroup("Developer")))material = Material.IRON_BARS;
            else if (rank.getParent().equals(new RankGroup("Brain")))material = Material.REPEATER;
            else if (rank.getParent().equals(new RankGroup("Master")))material = Material.FISHING_ROD;
            else material = Material.STONE;

            builder.addItem(new ItemBuilder(material, player).setName("§6" + rankName).setLore("§7Click to choose this rank!"), event -> {
                rankToAdd = rank;
                DefaultSounds.pling.play(player);
                openThirtStaffAddInv(player);
            });
        }

        builder.open();
    }
    private static void openThirtStaffAddInv(BukkitPlayer player){
        InventoryBuilder builder = new InventoryBuilder(player, "§6Staff add - " + toAdd.getPlayer().getName(), 5);

        builder.setItem(new ItemBuilder(Material.PLAYER_HEAD, player).setSkull(toAdd.getUniqueID()).setName("§6" + toAdd.getPlayer().getName()), 13, event -> event.setCancelled(true));
        builder.setItem(new ItemBuilder(Material.GREEN_DYE, player).setName("§6Confirm").setLore("§7Click to confirm!"), 30, event -> {
            new StaffAddAction(player.api(), rankToAdd).perform();
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
            if (Bukkit.getOnlinePlayers().contains(toAdd.getPlayer())){
                Player targetPl = toAdd.getPlayer();
                targetPl.getWorld().strikeLightningEffect(targetPl.getLocation());
                targetPl.getWorld().strikeLightningEffect(targetPl.getLocation());
                BukkitCore.getInstance().getCurrentGame().getTablist().reload();
            }
        });
        builder.setItem(new ItemBuilder(Material.RED_DYE, player).setName("§6Cancel").setLore("§7Click to cancel!"), 32, event -> {
            DefaultSounds.levelUP.play(player);
            player.getPlayer().closeInventory();
        });

        builder.fillEmpty();
        builder.open();
    }

}
