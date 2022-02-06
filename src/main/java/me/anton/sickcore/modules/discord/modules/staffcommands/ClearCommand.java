package me.anton.sickcore.modules.discord.modules.staffcommands;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand extends SlashCommand {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clears a specific amount of messages";
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public List<OptionData> initOptionData() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.INTEGER, "amount", "Amount of messages", true));
        return data;
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, IDiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        DiscordModule module = StaffCommandModule.getInstance().getModule();

        int amount = (int) event.getOption("amount").getAsDouble();

        IAPIPlayer apiPlayer = getApiPlayer(user);

        if (!apiPlayer.isVerified()) {event.replyEmbeds(DiscordMessages.getNotVerified(getMember(user))).setEphemeral(true).queue();return;}

        if (!apiPlayer.isTeam()){event.replyEmbeds(new EmbedBuilder(getMember(user)).setContent("This is a staff command!").setTitle("No Staff").build()).setEphemeral(true).queue();return;}

        event.getTextChannel().getHistory().retrievePast(amount).queue(list ->{
            list.forEach(message -> message.delete().queue());
        });
    }
}
