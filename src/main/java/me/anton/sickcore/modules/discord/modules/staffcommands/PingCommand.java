package me.anton.sickcore.modules.discord.modules.staffcommands;

import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class PingCommand extends SlashCommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public boolean isStaff() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Shows the ping of the bot";
    }


    @Override
    public List<OptionData> initOptionData() {
        return new ArrayList<>();
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {
        hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Ping").setContent("My reaction time: " + DiscordModule.getInstance().getJda().getGatewayPing() + "ms").build()).queue();
    }
}
