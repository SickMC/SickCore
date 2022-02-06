package me.anton.sickcore.modules.discord.modules.staffcommands.cloud;

import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloudCommand extends SlashCommand {
    @Override
    public String getName() {
        return "cloud";
    }

    @Override
    public String getDescription() {
        return "Controls the cloud!";
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public List<OptionData> initOptionData() {
        return new ArrayList<>();
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        List<SlashSubCommand> subCommands = Arrays.asList(
                new CloudListCommand(this),
                new CloudStopCommand(this),
                new CloudStartCommand(this)
        );
        return subCommands;
    }

    @Override
    public void execute(User user, IDiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        event.replyEmbeds(new EmbedBuilder(DiscordModule.getInstance().getJda().getSelfUser()).setTitle("Cloud").setContent("Cloud is running!").build()).queue();
    }
}
