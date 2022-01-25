package me.anton.sickcore.modules.discord.handlers.command;
import lombok.Data;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.handlers.messages.DiscordMessages;
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

    public abstract String getDescription();

    public abstract boolean isEphemeral();

    public String getPermission(){
        return "";
    }

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
            hook.setEphemeral(isEphemeral());
            execute(user, hook, event);
            return;
        }
        boolean called = false;
        for (SlashSubCommand subCommand : getSlashSubCommands()) {
            if(called) continue;
            if(subCommand.getName().equalsIgnoreCase(subCommandName)){
                hook.setEphemeral(subCommand.isEphemeral());
                subCommand.execute(user, hook, event);
                called = true;
            }
        }
    }

    public IAPIPlayer getApiPlayer(User user){
        if (DiscordAPIPlayerAdapter.isVerified(user))return new APIPlayer(user.getId());
        else return null;
    }



}