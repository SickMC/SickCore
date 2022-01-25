package me.anton.sickcore.modules.discord.modules.verify;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.modules.ranks.RankUpdate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class VerifyCommand extends SlashCommand {
    @Override
    public String getName() {
        return "verify";
    }

    @Override
    public String getDescription() {
        return "Verifiziere deinen Minecraft Account";
    }

    @Override
    public boolean isEphemeral() {
        return false;
    }

    @Override
    public List<OptionData> initOptionData() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.INTEGER, "code", "Code zum verifizieren", true));
        return data;
    }

    @Override
    public List<SlashSubCommand> initSlashSubCommands() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {
        DiscordModule module = DiscordModule.getInstance();
        VerifyModule verifyModule = VerifyModule.getVerifyModule();

        int verifyID = (int) event.getOption("code").getAsLong();

        if (DiscordAPIPlayerAdapter.isVerified(user)){hook.sendMessageEmbeds(getAlreadyVerified(user)).setEphemeral(false).queue();return;}

        if (!verifyModule.getVerifyList().containsValue(verifyID)){hook.sendMessageEmbeds(getWrongCodeEmbed(user)).setEphemeral(true).queue();return;}

        IBungeePlayer player = verifyModule.getVerifyListReturn().get(verifyID);

        verify(player, user.getId());

        verifyModule.getVerifyListReturn().remove(verifyID, player);
        verifyModule.getVerifyList().remove(player, verifyID);

        sendLog(user, player);
        player.sendMessage("§7Your account is now linked with §6" + event.getMember().getUser().getAsTag() + "§7!", "§7Dein Account ist nun mit §6" + event.getMember().getUser().getAsTag() + "§7 verbunden!");
        hook.sendMessageEmbeds(getVerified(user)).queue();
        hook.deleteOriginal().queue();
    }

    private void verify(IBungeePlayer player, String userID){
        IAPIPlayer iapiPlayer = player.api();
        iapiPlayer.setDiscordID(userID);
        new RankUpdate(userID);
    }
    private void sendLog(User user, IBungeePlayer player){
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**Verified | SickMC**")
                .setDescription(user.getAsMention() + " is now verified with mc: " + player.api().getName())
                .setFooter(DiscordMessages.getFooter(user), DiscordMessages.getAvatarURL(user))
                .setColor(Color.ORANGE).build();
        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.discordLogChannel).sendMessageEmbeds(embed).queue();
    }

    private MessageEmbed getWrongCodeEmbed(User user){
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("**Ungültiger Code**")
                .setDescription("Dieser Code konnte nicht gefunden werden!")
                .setColor(Color.ORANGE)
                .setFooter(DiscordMessages.getFooter(user));

        return builder.build();
    }

    private MessageEmbed getAlreadyVerified(User user){
        EmbedBuilder en = new EmbedBuilder()
                .setTitle("**Already verified**")
                .setDescription("You are already verified!")
                .setColor(Color.ORANGE)
                .setFooter(DiscordMessages.getFooter(user));

        EmbedBuilder de = new EmbedBuilder()
                .setTitle("**Bereits verifiziert**")
                .setDescription("Du bist bereits verifiziert!")
                .setColor(Color.ORANGE)
                .setFooter(DiscordMessages.getFooter(user));

        if (new APIPlayer(user.getId()).getLanguage() == Language.ENGLISCH)return en.build();
        else return de.build();
    }

    private MessageEmbed getVerified(User user){
        EmbedBuilder en = new EmbedBuilder()
                .setTitle("**Verified**")
                .setDescription("You are now verified!")
                .setColor(Color.ORANGE)
                .setFooter(DiscordMessages.getFooter(user));

        EmbedBuilder de = new EmbedBuilder()
                .setTitle("**Verifiziert**")
                .setDescription("Du bist nun verifiziert!")
                .setColor(Color.ORANGE)
                .setFooter(DiscordMessages.getFooter(user));

        if (new APIPlayer(user.getId()).getLanguage() == Language.ENGLISCH)return en.build();
        else return de.build();
    }

}
