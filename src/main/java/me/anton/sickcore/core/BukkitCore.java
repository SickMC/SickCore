package me.anton.sickcore.core;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.anton.sickcore.api.handler.listeners.bukkit.BukkitListenerProvider;
import me.anton.sickcore.api.handler.listeners.bukkit.events.entity.*;
import me.anton.sickcore.api.handler.listeners.bukkit.events.other.HeadDataBaseLoadEventHandler;
import me.anton.sickcore.api.handler.listeners.bukkit.events.player.*;
import me.anton.sickcore.api.handler.listeners.bukkit.events.vehicle.*;
import me.anton.sickcore.api.handler.listeners.bukkit.events.world.*;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import me.anton.sickcore.games.defaults.all.HeadDBAPI;
import me.anton.sickcore.games.defaults.all.InvSee;
import me.anton.sickcore.games.defaults.all.LagCommand;
import me.anton.sickcore.games.defaults.all.appereance.UnknownCommandProvider;
import me.anton.sickcore.games.defaults.all.nick.AutoNickCommand;
import me.anton.sickcore.games.defaults.all.nick.NickCommand;
import me.anton.sickcore.games.defaults.all.nick.NickListCommand;
import me.anton.sickcore.games.defaults.all.nick.UnnickCommand;
import me.anton.sickcore.games.defaults.all.vanish.UnVanishCommand;
import me.anton.sickcore.games.defaults.all.vanish.VanishAction;
import me.anton.sickcore.games.defaults.all.vanish.VanishCommand;
import me.anton.sickcore.games.defaults.all.vanish.VanishListCommand;
import me.anton.sickcore.core.game.GameHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class BukkitCore extends Core {

    @Getter
    private static BukkitCore instance;
    private final JavaPlugin plugin;
    private List<IBukkitPlayer> onlineBukkitPlayers;
    private List<IAPIPlayer> onlineAPIPlayers;
    private final PaperCommandManager manager;
    private final BukkitListenerProvider provider;
    private final GameHandler handler;

    public BukkitCore(JavaPlugin plugin){
        instance = this;
        this.plugin = plugin;
        this.onlineBukkitPlayers = new ArrayList<>();
        this.onlineAPIPlayers = new ArrayList<>();
        this.manager = new PaperCommandManager(plugin);
        manager.enableUnstableAPI("brigadier");
        manager.enableUnstableAPI("help");
        this.provider = new BukkitListenerProvider();
        this.handler = new GameHandler();
    }

    public void onLoad(){
        register();
        handler.loadGames();
    }

    public void onUnLoad(){
        handler.unloadGame();
    }

    private void register(){
        List list = Arrays.asList(
                //Player
                new PlayerDeathEventHandler(),
                new PlayerJoinEventHandler(),
                new PlayerQuitEventHandler(),
                new PlayerLoginEventHandler(),
                new PlayerInteractEventHandler(),
                new PlayerItemInteractEventHandler(),
                new BlockBreakEventHandler(),
                new BlockPlaceEventHandler(),
                new PlayerToggleSneakEventHandler(),
                new PlayerMoveEventHandler(),
                new PlayerChangeWorldEventHandler(),
                new PlayerFishEventHandler(),
                new PlayerInventoryOpenEventHandler(),
                new PlayerInteractInventoryEventHandler(),
                new PlayerInventoryCloseEventHandler(),
                new PlayerDropItemEventHandler(),
                new PlayerPickUpItemEventHandler(),
                new PlayerFoodLevelChangeEventHandler(),
                new PlayerDamageEventHandler(),
                new PlayerDamageByEntityEventHandler(),
                new PlayerDamageByPlayerEventHandler(),
                new PlayerInteractAtEntityEventHandler(),
                new PlayerInteractAtPlayerEventHandler(),
                new PlayerInteractEntityEventHandler(),
                new PlayerBucketEmptyEventHandler(),
                new PlayerAsyncChatEventHandler(),
                new PlayerItemHeldEventHandler(),
                new PlayerCommandPreprocessEventHandler(),
                new PlayerCommandSendEventHandler(),
                new PlayerVelocityEventHandler(),
                new PlayerCraftItemEventHandler(),
                new PlayerNickUpdateEventHandler(),
                new PlayerSwapHandItemsEventHandler(),
                new PlayerElytraEventHandler(),
                new EntityDeathByPlayerEventHandler(),
                new PlayerRegainHealthEventHandler(),
                new PlayerChangeBlockEventHandler(),
                new PlayerAdvancementDoneEventHandler(),
                new PlayerInventoryMoveItemEventHandler(),
                new PlayerLevelChangeEventHandler(),
                new PlayerUnknownCommandEvent(),

                //Entity
                new EntityPickUpItemEventHandler(),
                new EntityDamageEventHandler(),
                new EntityDamageByEntityEventHandler(),
                new EntityInteractEventHandler(),
                new EntityRegainHealthEventHandler(),
                new EntityDeathEventHandler(),
                new EntityToggleGlideEventHandler(),
                new EntitySpawnEventHandler(),

                //Vehicle
                new VehicleDestroyByPlayerEventHandler(),
                new VehicleDestroyByEntityEventHandler(),
                new VehicleExitByPlayerEventHandler(),
                new VehicleExitByEntityEventHandler(),
                new VehicleDamageByPlayerEventHandler(),
                new VehicleDamageByEntityEventHandler(),

                //world
                new BlockMoveEventHandler(),
                new WeatherChangeEventHandler(),
                new EntityExplodeEventHandler(),
                new CreatureSpawnEventHandler(),
                new PlayerInventoryDragEventHandler(),
                new ChunkLoadEventHandler(),
                new EntityChangeBlockEventHandler(),
                new ItemSpawnEventHandler(),
                new HangingBreakEventHandler(),
                new HangingPlaceEventHandler(),

                //other
                new HeadDataBaseLoadEventHandler()
        );

        list.forEach(handler -> plugin.getServer().getPluginManager().registerEvents((Listener) handler, plugin));

        provider.register(new HeadDBAPI());

        manager.registerCommand(new LagCommand(), true);
        manager.registerCommand(new NickCommand(), false);
        manager.registerCommand(new AutoNickCommand(), false);
        manager.registerCommand(new UnnickCommand(), false);
        manager.registerCommand(new NickListCommand(), false);
        manager.registerCommand(new InvSee(), false);

        provider.register(new UnknownCommandProvider());

        //Vanish
        manager.registerCommand(new VanishCommand(), false);
        provider.register(new VanishAction());
        manager.registerCommand(new UnVanishCommand(), false);
        manager.registerCommand(new VanishListCommand(), false);
    }

    public List<IBukkitPlayer> getOnlineBukkitPlayers(){
        return onlineBukkitPlayers;
    }

    public List<IAPIPlayer> getOnlineAPIPlayers() {
        return onlineAPIPlayers;
    }
}
