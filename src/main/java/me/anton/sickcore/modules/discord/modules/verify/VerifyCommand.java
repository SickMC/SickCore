package me.anton.sickcore.modules.discord.modules.verify;

import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.modules.ranks.RankUpdate;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

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
    public boolean isStaff() {
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
    }

    private void verify(IBungeePlayer player, String userID){
        IAPIPlayer iapiPlayer = player.api();
        iapiPlayer.setDiscordID(userID);
        new RankUpdate(userID);
    }
    private void sendLog(User user, IBungeePlayer player){
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder()
                .setTitle("Verified")
                .setContent(user.getAsMention() + " is now verified with mc: " + player.api().getName()).build();

        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.discordLogChannel).sendMessageEmbeds(embed).queue();
    }

    private MessageEmbed getWrongCodeEmbed(User user){
        MessageEmbed embed = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(DiscordModule.getInstance().getMainGuild().getMember(user))
                .setTitle("Ungültiger Code")
                .setContent("Dieser Code konnte nicht gefunden werden!").build();

        return embed;
    }

    private MessageEmbed getAlreadyVerified(User user){
        MessageEmbed en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(DiscordModule.getInstance().getMainGuild().getMember(user))
                .setTitle("Already verified")
                .setContent("You are already verified!").build();

        MessageEmbed de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(DiscordModule.getInstance().getMainGuild().getMember(user))
                .setTitle("Bereits verifiziert")
                .setContent("Du bist bereits verifiziert!").build();

        return new DiscordPlayer(DiscordModule.getInstance().getMainGuild().getMember(user)).getEmbed(en,de);
    }

    private MessageEmbed getVerified(User user){
        MessageEmbed en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(DiscordModule.getInstance().getMainGuild().getMember(user))
                .setTitle("Verified")
                .setContent("You are now verified!").build();

        MessageEmbed de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(DiscordModule.getInstance().getMainGuild().getMember(user))
                .setTitle("Verifiziert")
                .setContent("Du bist nun verifiziert!").build();


        return new DiscordPlayer(DiscordModule.getInstance().getMainGuild().getMember(user)).getEmbed(en,de);
    }

}
