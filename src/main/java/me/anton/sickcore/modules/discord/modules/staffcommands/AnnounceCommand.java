package me.anton.sickcore.modules.discord.modules.staffcommands;

import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class AnnounceCommand extends SlashCommand {
    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Creates Announce Embed in News Channel";
    }

    @Override
    public List<OptionData> initOptionData() {
        List<OptionData> optionData = new ArrayList<>();
        optionData.add(new OptionData(OptionType.STRING, "title", "Sets title of embed", true));
        optionData.add(new OptionData(OptionType.STRING, "content", "Sets content of embed", true));
        optionData.add(new OptionData(OptionType.MENTIONABLE, "mention", "Adds a ping to the embed", true));

        return optionData;
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return null;
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {

    }
}
