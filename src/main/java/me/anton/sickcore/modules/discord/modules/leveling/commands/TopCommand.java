package me.anton.sickcore.modules.discord.modules.leveling.commands;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import me.anton.sickcore.modules.discord.handlers.player.DiscordGamePlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.*;

public class TopCommand extends SlashCommand {
    @Override
    public String getName() {
        return "top";
    }

    @Override
    public boolean isStaff() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Shows a ranking of all members";
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
    public void execute(User user, DiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        if (!event.getTextChannel().getId().equals(DiscordIds.botchatchannel)) {
            hook.sendMessageEmbeds(DiscordMessages.getOnlyBotChat(getMember(user))).setEphemeral(true).queue();
            return;
        }
        DatabaseModel model = DiscordModule.getInstance().getGamePlayer();

        List<DiscordGamePlayer> members = new ArrayList<>();
        model.getAllValues().forEach(document -> {
            members.add(new DiscordGamePlayer(document.getString("userID")));
        });

        members.sort(Comparator.comparing(DiscordGamePlayer::getPoints));

        EmbedBuilder builder = new EmbedBuilder(user).setTitle("Top").setContent(">>> 1.  " + getMember(members.get(members.size() -1).getId()).getAsMention() + ": " + members.get(members.size() -1).getPoints() + "\n" +
                " 2.  " + getMember(members.get(members.size() - 2).getId()).getAsMention() + ": " + members.get(members.size() - 2).getPoints() + "\n" +
                " 3.  " + getMember(members.get(members.size() - 3).getId()).getAsMention() + ": " + members.get(members.size() - 3).getPoints() + "\n" +
                " 4.  " + getMember(members.get(members.size() - 4).getId()).getAsMention() + ": " + members.get(members.size() - 4).getPoints() + "\n" +
                " 5.  " + getMember(members.get(members.size() - 5).getId()).getAsMention() + ": " + members.get(members.size() - 5).getPoints());

        hook.sendMessageEmbeds(builder.build()).queue();
    }

    private Member getMember(String id) {
        return DiscordModule.getInstance().getMainGuild().getMemberById(id);
    }
}