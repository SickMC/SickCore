package me.anton.sickcore.modules.discord;

import lombok.Getter;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.utils.common.FileUtils;
import me.anton.sickcore.api.utils.common.Logger;
import me.anton.sickcore.core.Core;
import me.anton.sickcore.core.module.proxiedModule.ProxiedIModule;
import me.anton.sickcore.modules.discord.handlers.command.SelectionMenuListener;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommandBuilder;
import me.anton.sickcore.modules.discord.modules.DiscordModuleHandler;
import me.anton.sickcore.modules.discord.modules.leveling.commands.LevelCommand;
import me.anton.sickcore.modules.discord.modules.leveling.commands.TopCommand;
import me.anton.sickcore.modules.discord.modules.staffcommands.AnnounceCommand;
import me.anton.sickcore.modules.discord.modules.staffcommands.ClearCommand;
import me.anton.sickcore.modules.discord.modules.staffcommands.PingCommand;
import me.anton.sickcore.modules.discord.modules.staffcommands.cloud.CloudCommand;
import me.anton.sickcore.modules.discord.modules.verify.VerifyCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

@Getter
public class DiscordModule implements ProxiedIModule {

    private DiscordModuleHandler moduleHandler;
    @Getter
    public static DiscordModule instance;
    private Document model;
    private DatabaseModel gamePlayer;
    private DatabaseModel discordModel;

    private JDA jda;
    private JDABuilder builder;
    private List<GatewayIntent> intents;
    private Guild mainGuild;
    private Guild secondGuild;
    private SlashCommandBuilder commandBuilder;

    @Override
    public void load() {
        if (!Core.getInstance().bungee().isMainProxy())return;
        instance = this;
        this.discordModel = new DatabaseModel("discord");
        this.commandBuilder = new SlashCommandBuilder();
        moduleHandler = new DiscordModuleHandler();
        model = discordModel.getDocument(Finder.stringFinder("bot", "sickmc"));
        this.gamePlayer = new DatabaseModel("discordPlayer");
        register();
    }

    @Override
    public void unload() {
        if (!Core.getInstance().bungee().isMainProxy())return;
        shutdown();
    }

    @Override
    public void register() {
        start();
        Arrays.asList(new CloudCommand(), new ClearCommand(), new PingCommand(), new VerifyCommand(), new AnnounceCommand(), new LevelCommand(), new TopCommand()).forEach(command -> this.commandBuilder.registerCommand(command));
        getJda().addEventListener(new SelectionMenuListener());
    }

    public void start(){
        this.intents = Arrays.asList(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS
        );

        Logger.info("Starting Discordbot...", this.getClass());
        JDABuilder jdaBuilder = JDABuilder.createDefault((String) readFromConfig("token"), intents).setMemberCachePolicy(MemberCachePolicy.ALL);
        jdaBuilder.setActivity(Activity.playing("on play.sickmc.net"));
        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        this.builder = jdaBuilder;

        try {
            this.jda = jdaBuilder.build();
            this.jda.awaitReady();
            for (Guild guild : this.jda.getGuilds()) {
                Logger.info("[Discord] Loading members of guild: " + guild, this.getClass());
                guild.loadMembers().get();
            }
            this.jda.addEventListener(this.commandBuilder);
            this.mainGuild = jda.getGuildById((String)readFromConfig("mainguildID"));
            this.secondGuild = jda.getGuildById((String) readFromConfig("secondguildID"));
            this.moduleHandler.loadModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        Logger.info("Stopping Discordbot...", this.getClass());
        builder.setStatus(OnlineStatus.OFFLINE);
    }

    public void shutdown(){
        stop();
        moduleHandler.unLoadModules();
    }

    public Object readFromConfig(String key){
        return model.get(key);
    }

    public Document getCommands(){
        return FileUtils.getSubDocument("registeredCommands", model);
    }
}
