package me.anton.sickcore.modules.discord.handlers.command;

import lombok.Data;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class SlashSubCommand {

    private final SlashCommand mainCommand;

    private boolean subCommandDataCached = false;
    private final List<SubcommandData> cachedSubCommandData = new ArrayList<>();

    private boolean aliasesCached = false;
    private final List<String> aliases = new ArrayList<>();

    private boolean subOptionsDataCached = false;
    private final List<OptionData> cachedSubOptionData = new ArrayList<>();

    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean isEphemeral();

    public abstract List<String> initAliases();
    public List<String> getAliases(){
        if(this.aliasesCached) return this.aliases;
        this.aliases.addAll(initAliases());
        this.aliasesCached = true;
        return this.aliases;
    }

    public abstract List<OptionData> initSubOptionData();
    public List<OptionData> getSubOptionData(){
        if (this.subOptionsDataCached) return this.cachedSubOptionData;
        this.cachedSubOptionData.addAll(initSubOptionData());
        this.subOptionsDataCached = true;
        return this.cachedSubOptionData;
    }

    public List<SubcommandData> initSubCommandData(){
        List<SubcommandData> data = new ArrayList<>();
        data.add(new SubcommandData(getName(), getDescription()).addOptions(getSubOptionData()));
        for (String alias : getAliases()) {
            data.add(new SubcommandData(alias, getDescription()).addOptions(getSubOptionData()));
        }
        return data;
    }
    public List<SubcommandData> getOptionData(){
        if(this.subCommandDataCached) return this.cachedSubCommandData;
        this.cachedSubCommandData.addAll(initSubCommandData());
        this.subCommandDataCached = true;
        return this.cachedSubCommandData;
    }

    public String getPermission(){
        return mainCommand.getPermission();
    }

    public abstract void execute(User user, InteractionHook hook, SlashCommandEvent event);

    public IAPIPlayer getApiPlayer(User user){
        return this.mainCommand.getApiPlayer(user);
    }

    public boolean hasPermission(User user, String permission){
        return this.mainCommand.hasPermission(user, permission);
    }

    public void sendNoVerifyMessage(SlashCommandEvent event){
        this.mainCommand.sendNoVerifyMessage(event);
    }

    public void sendNoPermissions(SlashCommandEvent event) { this.mainCommand.sendNoPermissions(event); }

    public boolean permissionCheck(User user, SlashCommandEvent hook){
        if(!this.mainCommand.permissionCheck(user, hook)) return false;
        boolean state = hasPermission(user, getPermission());
        if(!state){
            sendNoPermissions(hook);
        }
        return state;
    }
}