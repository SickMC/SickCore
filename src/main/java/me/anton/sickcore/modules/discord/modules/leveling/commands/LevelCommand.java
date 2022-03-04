package me.anton.sickcore.modules.discord.modules.leveling.commands;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class LevelCommand extends SlashCommand {
    @Override
    public String getName() {
        return "level";
    }

    @Override
    public boolean isStaff() {
        return false;
    }

    @Override
    public String getDescription() {
        return "Gives information about a players discord level";
    }

    @Override
    public List<OptionData> initOptionData() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.MENTIONABLE, "member", "Sends information about a specific player", false));

        return data;
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, DiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        if (!event.getTextChannel().getId().equals(DiscordIds.botchatchannel)){
            hook.sendMessageEmbeds(DiscordMessages.getOnlyBotChat(getMember(user))).setEphemeral(true).queue();
            return;
        }
        DatabaseModel model = DiscordModule.getInstance().getGamePlayer();

        if (!model.documentExists(Finder.stringFinder("userID", event.getMember().getUser().getId()))){
            model.createDocument(new Document("userID", event.getMember().getUser().getId()).append("points", 1).append("enabled", false));
            if (DiscordAPIPlayerAdapter.isVerified(event.getMember().getUser().getId())){
                Document document = model.getDocument(Finder.stringFinder("userID", event.getMember().getUser().getId()));
                document.replace("enabled", true);
                model.updateDocument(Finder.stringFinder("userID", event.getMember().getUser().getId()), document);
            }
        }

        if (event.getOption("member") == null){
            if (!DiscordAPIPlayerAdapter.isVerified(user)) {
                hook.sendMessageEmbeds(DiscordMessages.getNotVerified(getMember(user))).setEphemeral(true).queue();
                return;
            }

            hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Level").setContent("Your level is **" + player.getLevel().getName()
                    + "**\nProgress to **" + player.getLevel().getNext().getName() + "**:\n >>> " + player.getPoints() + "/" + player.getLevel().getEnd()).build()).queue();
        }else {
            Member member = event.getOption("member").getAsMember();
            DiscordPlayer target = new DiscordPlayer(member);
            if (!model.documentExists(Finder.stringFinder("userID", member.getUser().getId()))){
                model.createDocument(new Document("userID", member.getUser().getId()).append("points", 1).append("enabled", false));
                if (DiscordAPIPlayerAdapter.isVerified(member.getUser().getId())){
                    Document document = model.getDocument(Finder.stringFinder("userID", member.getUser().getId()));
                    document.replace("enabled", true);
                    model.updateDocument(Finder.stringFinder("userID", member.getUser().getId()), document);
                }
            }

            if (!DiscordAPIPlayerAdapter.isVerified(member)) {
                MessageEmbed builder = new EmbedBuilder(event.getMember()).setTitle("Not verified").setContent(member.getAsMention() + " is not verified!").build();
                hook.sendMessageEmbeds(builder).setEphemeral(true).queue();
                return;
            }

            hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Level").setContent(member.getAsMention() + "'s level is **" + target.getLevel().getName()
                    + "**\nProgress to **" + target.getLevel().getNext().getName() + "**:\n >>> " + target.getPoints() + "/" + target.getLevel().getEnd()).build()).queue();
        }
    }
}
