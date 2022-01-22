package me.anton.sickcore.modules.discord;

import lombok.Getter;
import me.anton.sickcore.api.utils.common.system.Logger;
import me.anton.sickcore.modules.discord.handlers.utils.Secrets;
import me.anton.sickcore.core.module.IModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommandBuilder;
import me.anton.sickcore.modules.discord.modules.DiscordModuleHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;
import java.util.List;

@Getter
public class DiscordModule implements IModule {

    private DiscordModuleHandler moduleHandler;
    @Getter
    public static DiscordModule instance;

    private JDA jda;
    private JDABuilder builder;
    private List<GatewayIntent> intents;
    private Guild mainGuild;
    private SlashCommandBuilder commandBuilder;

    @Override
    public void load() {
        instance = this;
        this.commandBuilder = new SlashCommandBuilder();
        moduleHandler = new DiscordModuleHandler();
        register();
    }

    @Override
    public void unload() {
        shutdown();
    }

    @Override
    public void register() {
        start();
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

        Logger.info("Starting Discordbot...");
        JDABuilder jdaBuilder = JDABuilder.createDefault(Secrets.token, intents);
        jdaBuilder.setActivity(Activity.playing("on play.sickmc.net"));
        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        this.builder = jdaBuilder;

        try {
            this.jda = jdaBuilder.build();
            this.jda.awaitReady();
            for (Guild guild : this.jda.getGuilds()) {
                Logger.info("[Discord] Loading members of guild: " + guild);
                guild.loadMembers().get();
            }
            this.jda.addEventListener(this.commandBuilder);
            this.mainGuild = jda.getGuildById("908087446229643314");
            this.moduleHandler.loadModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        Logger.info("Stopping Discordbot...");
        builder.setStatus(OnlineStatus.OFFLINE);
    }

    public void shutdown(){
        stop();
        moduleHandler.unLoadModules();
    }

}
