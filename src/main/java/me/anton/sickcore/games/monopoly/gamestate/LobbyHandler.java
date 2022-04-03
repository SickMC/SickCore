package me.anton.sickcore.games.monopoly.gamestate;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitHandler;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.BukkitPlayer;
import me.anton.sickcore.api.utils.minecraft.bukkit.player.TitleBuilder;
import me.anton.sickcore.oldcore.BukkitCore;
import me.anton.sickcore.games.monopoly.MonopolyGame;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class LobbyHandler extends BukkitHandler {

    @Override
    public void onPlayerJoin(PlayerJoinEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (!MonopolyGame.getInstance().getCurrent().equals(GameState.LOBBY))return;
        rawEvent.joinMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> {
            APIPlayer all = new APIPlayer(player.getUniqueId());
            if (bukkitPlayer.api().hasAutoNick())
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.getNickDisplayName() + "§7joined the game §8(" + Bukkit.getOnlinePlayers() + "/8)§7!", bukkitPlayer.getNickDisplayName() + "§7hat den Server betreten§8(" + Bukkit.getOnlinePlayers() + "/8)§7!"));
            else
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.api().getDisplayName() + "§7joined the game §8(" + Bukkit.getOnlinePlayers() + "/8)§7!", bukkitPlayer.api().getDisplayName() + "§7hat Monopoly betreten §8(" + Bukkit.getOnlinePlayers() + "/8)§7!"));
        });
        startCheck();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (!MonopolyGame.getInstance().getCurrent().equals(GameState.LOBBY))return;
        rawEvent.quitMessage(null);
        Bukkit.getOnlinePlayers().forEach(player -> {
            APIPlayer all = new APIPlayer(player.getUniqueId());
            if (!bukkitPlayer.isNicked())
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.api().getDisplayName() + "§7quit the game §8(" + Bukkit.getOnlinePlayers() + "/8)§7!", bukkitPlayer.api().getDisplayName() + "§7hat den Server verlassen§8(" + Bukkit.getOnlinePlayers() + "/8)§7!"));
            else
                all.bukkit().getPlayer().sendMessage((String) all.languageObject(bukkitPlayer.getNickDisplayName() + "§7quit the game §8(" + Bukkit.getOnlinePlayers() + "/8)§7!", bukkitPlayer.getNickDisplayName() + "§7hat den Server verlassen§8(" + Bukkit.getOnlinePlayers() + "/8)§7!"));
        });
        startCheck();
    }

    @Override
    public void onPlayerAsyncChat(AsyncChatEvent rawEvent, BukkitPlayer bukkitPlayer) {
        if (!MonopolyGame.getInstance().getCurrent().equals(GameState.LOBBY))return;
        rawEvent.getPlayer().getServer().getOnlinePlayers().forEach(player -> {
            if (!bukkitPlayer.isNicked())
                player.sendMessage(Component.text(bukkitPlayer.api().getDisplayName() + "§8» §7").append(rawEvent.message().color(TextColor.color(11184810))));
            else
                player.sendMessage(Component.text(bukkitPlayer.getNickDisplayName() + "§8» §7").append(rawEvent.message().color(TextColor.color(11184810))));
        });
        rawEvent.setCancelled(true);
    }

    private void startCheck(){
        switch (Bukkit.getOnlinePlayers().size()){
            case 1: {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendActionBar(Component.text("§61 §7more player needed to start!"));
                    new BukkitPlayer(player).playSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                });
            }
            case 2, 3, 4, 5, 6, 7:{
                Bukkit.getOnlinePlayers().forEach(player -> {
                    AtomicInteger taskID = new AtomicInteger();
                    AtomicInteger timeleft = new AtomicInteger(30);
                    taskID.set(Bukkit.getScheduler().runTaskTimer(BukkitCore.getInstance().getPlugin(), () -> {
                        player.sendActionBar(Component.text("§61 §7more player can join!"));
                        player.showBossBar(BossBar.bossBar(Component.text("§6" + timeleft.get() + " §7seconds until game starts"), (float) timeleft.get(), BossBar.Color.RED, BossBar.Overlay.NOTCHED_20));
                        timeleft.set(timeleft.get() - 1);
                        switch (timeleft.get()) {
                            case 30, 15, 10, 5, 3, 2, 1 -> {
                                new TitleBuilder("§6" + timeleft.get(), "§7seconds until game starts", 2).sendTitle(player);
                                new BukkitPlayer(player).playSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                            }
                        }
                        if (timeleft.get() == 0){
                            MonopolyGame.getInstance().getHandler().setGameState(GameState.GAME);
                            Bukkit.getScheduler().cancelTask(taskID.get());
                        }
                    }, 0, 20).getTaskId());

                    new BukkitPlayer(player).playSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                });
            }
            case 8:{
                Bukkit.getOnlinePlayers().forEach(player -> {
                    AtomicInteger taskID = new AtomicInteger();
                    AtomicInteger timeleft = new AtomicInteger(10);
                    taskID.set(Bukkit.getScheduler().runTaskTimer(BukkitCore.getInstance().getPlugin(), () -> {
                        player.sendActionBar(Component.text("§6Game starts in 10 seconds!"));
                        player.showBossBar(BossBar.bossBar(Component.text("§6" + timeleft.get() + " §7seconds until game starts"), (float) timeleft.get(), BossBar.Color.RED, BossBar.Overlay.NOTCHED_10));
                        timeleft.set(timeleft.get() - 1);
                        switch (timeleft.get()){
                            case 10, 5,3,2,1-> {
                                new TitleBuilder("§6" + timeleft.get(), "§7seconds until game starts", 2).sendTitle(player);
                                new BukkitPlayer(player).playSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                            }
                        }
                        if (timeleft.get() == 0){
                            MonopolyGame.getInstance().getHandler().setGameState(GameState.GAME);
                            Bukkit.getScheduler().cancelTask(taskID.get());
                        }
                    }, 0, 20).getTaskId());

                    new BukkitPlayer(player).playSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                });
            }
        }
    }

}
