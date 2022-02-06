package me.anton.sickcore.modules.discord.modules.staffcommands;

import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.IMentionable;
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
        optionData.add(new OptionData(OptionType.MENTIONABLE, "mention", "Adds a ping to the embed", false));

        return optionData;
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, IDiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        DiscordModule module = StaffCommandModule.getInstance().getModule();

        String rawtitle = event.getOption("title").getAsString();
        String rawContent = event.getOption("content").getAsString();
        IMentionable mentionable = null;
        if (event.getOption("mention") != null)mentionable = event.getOption("mention").getAsMentionable();

        String content = rawContent.replace("%n", "\n");

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle(rawtitle)
                .setContent(content);

        if (mentionable != null)builder.mention(mentionable);

        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.newsChannel).sendMessageEmbeds(builder.build()).queue();
        event.reply("Message send!").queue();
    }
}
