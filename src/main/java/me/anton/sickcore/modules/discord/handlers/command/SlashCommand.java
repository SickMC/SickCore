package me.anton.sickcore.modules.discord.handlers.command;
import lombok.Data;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class SlashCommand {

    private boolean optionDataCached = false;
    private final List<OptionData> cachedOptionData = new ArrayList<>();

    private boolean slashSubCommandsCached = false;
    private final List<SlashSubCommand> cachedSlashSubCommands = new ArrayList<>();

    public abstract String getName();

    public abstract boolean isStaff();

    public abstract String getDescription();

    public abstract List<OptionData> initOptionData();
    public List<OptionData> getOptionData(){
        if(this.optionDataCached) return this.cachedOptionData;
        this.cachedOptionData.addAll(initOptionData());
        this.optionDataCached = true;
        return this.cachedOptionData;
    }

    public abstract List<SlashSubCommand> initSlashSubCommands();
    public List<SlashSubCommand> getSlashSubCommands(){
        if(this.slashSubCommandsCached) return this.cachedSlashSubCommands;
        this.cachedSlashSubCommands.addAll(initSlashSubCommands());
        this.slashSubCommandsCached = true;
        return this.cachedSlashSubCommands;
    }
    public List<SubcommandData> getSlashSubCommandData(){
        List<SubcommandData> data = new ArrayList<>();
        for (SlashSubCommand subCommand : getSlashSubCommands()) {
            data.addAll(subCommand.getOptionData());
        }
        return data;
    }

    public abstract void execute(User user, InteractionHook hook, SlashCommandEvent event);

    public void preExecute(User user, InteractionHook hook, SlashCommandEvent event){
        String subCommandName = event.getSubcommandName();
        if(subCommandName == null){
            if (isStaff() && !staffCheck(user, hook))return;
            execute(user, hook, event);
            return;
        }
        boolean called = false;
        for (SlashSubCommand subCommand : getSlashSubCommands()) {
            if(called) continue;
            if(subCommand.getName().equalsIgnoreCase(subCommandName)){
                if (subCommand.isStaff() && !subCommand.staffCheck(user, hook))return;
                subCommand.execute(user, hook, event);
                called = true;
            }
        }
    }

    public boolean isStaffChannel(SlashCommandEvent event){
        return event.getTextChannel().getId().equals(DiscordIds.staffCommandsChannel);
    }

    private boolean staffCheck(User user, InteractionHook hook){
        if (!DiscordAPIPlayerAdapter.isVerified(user)) {
            hook.sendMessageEmbeds(DiscordMessages.getNotVerified(getMember(user))).setEphemeral(true).queue();
            return false;
        }
        boolean state = new APIPlayer(user.getId()).isTeam();
        if(!state){
            hook.sendMessageEmbeds(DiscordMessages.getNoStaff(getMember(user))).setEphemeral(true).queue();
        }

        return state;
    }

    public IAPIPlayer getApiPlayer(User user){
        if (DiscordAPIPlayerAdapter.isVerified(user))return new APIPlayer(user.getId());
        else return null;
    }

    public Member getMember(User user){
        return DiscordModule.getInstance().getMainGuild().getMember(user);
    }

}