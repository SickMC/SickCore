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

    private boolean aliasesCached = false;
    private final List<String> aliases = new ArrayList<>();

    private boolean slashSubCommandsCached = false;
    private final List<SlashSubCommand> cachedSlashSubCommands = new ArrayList<>();

    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean isEphemeral();

    public String getPermission(){
        return "";
    }

    public abstract List<String> initAliases();
    public List<String> getAliases(){
        if(this.aliasesCached) return this.aliases;
        this.aliases.addAll(initAliases());
        this.aliasesCached = true;
        return this.aliases;
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
            if(!permissionCheck(user, event)) return;
            hook.setEphemeral(isEphemeral());
            execute(user, hook, event);
            return;
        }
        boolean called = false;
        for (SlashSubCommand subCommand : getSlashSubCommands()) {
            if(called) continue;
            if(subCommand.getName().equalsIgnoreCase(subCommandName)){
                if(!subCommand.permissionCheck(user, event)) return;
                hook.setEphemeral(subCommand.isEphemeral());
                subCommand.execute(user, hook, event);
                called = true;
            }
        }
        if(called) return;
        for (SlashSubCommand subCommand : getSlashSubCommands()) {
            if(called) continue;
            for (String alias : subCommand.getAliases()) {
                if(called) continue;
                if(alias.equalsIgnoreCase(subCommandName)){
                    if(!subCommand.permissionCheck(user, event)) return;
                    hook.setEphemeral(subCommand.isEphemeral());
                    subCommand.execute(user, hook, event);
                    called = true;
                }
            }
        }
    }

    public boolean isUserVerified(User user){
        return DiscordAPIPlayerAdapter.isVerified(user);
    }

    public void sendNoVerifyMessage(SlashCommandEvent event){
        DiscordModule discordModule = DiscordModule.getInstance();
        event.replyEmbeds(DiscordMessages.getNotVerified(event.getMember())).setEphemeral(true).queue();
    }

    public IAPIPlayer getApiPlayer(User user){
        if (isUserVerified(user))return new APIPlayer(user.getId());
        else return null;
    }

    public boolean hasPermission(User user, String permission){
        if(permission.isEmpty()) return true;
        if(!isUserVerified(user)) return false;
        return getApiPlayer(user).perm().hasPermission(permission);
    }

    public boolean permissionCheck(User user, SlashCommandEvent event){
        boolean state = hasPermission(user, getPermission());
        if(!state){
            sendNoPermissions(event);
        }
        return state;
    }

    public void sendNoPermissions(SlashCommandEvent event){
        DiscordModule discordModule = DiscordModule.getInstance();
        event.replyEmbeds(DiscordMessages.getNoPermission(event.getMember())).setEphemeral(true).queue();
    }

}