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

    private boolean subOptionsDataCached = false;
    private final List<OptionData> cachedSubOptionData = new ArrayList<>();

    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean isEphemeral();


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

}