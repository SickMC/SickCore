package me.anton.sickcore.modules.discord.modules.staffcommands.cloud;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
import eu.thesimplecloud.api.wrapper.IWrapperInfo;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CloudInfoCommand extends SlashSubCommand {
    public CloudInfoCommand(SlashCommand mainCommand) {
        super(mainCommand);
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Shows information about player/cloud-services";
    }

    @Override
    public List<OptionData> initSubOptionData() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "service", "Shows information about services", false));
        data.add(new OptionData(OptionType.STRING, "wrapper", "Shows information about wrappers", false));
        data.add(new OptionData(OptionType.STRING, "player", "Shows information about players", false));
        data.add(new OptionData(OptionType.STRING, "group", "Shows information about groups", false));
        return data;
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {
        if (event.getOption("player") != null){
            if(event.getOption("player") != null){
                ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(event.getOption("player").getAsString()).getBlockingOrNull();
                if(cloudPlayer == null){
                    hook.sendMessage("This player cannot be found!").queue();
                    return;
                }
                hook.sendMessage("```" + cloudPlayer.toString() + "```").queue();
                return;
            }
            if(event.getOption("wrapper") != null){
                IWrapperInfo wrapperInfo = CloudAPI.getInstance().getWrapperManager().getWrapperByName(event.getOption("wrapper").getAsString());
                if(wrapperInfo == null){
                    hook.sendMessage("This wrapper cannot be found!").queue();
                    return;
                }
                hook.sendMessage("```" + wrapperInfo.toString() + "```").queue();
                return;
            }

            if(event.getOption("service") != null){
                ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(event.getOption("service").getAsString());
                if(service == null){
                    hook.sendMessage("This service cannot be found!").queue();
                    return;
                }
                hook.sendMessage("```" + service.toString() + "```").queue();
                return;
            }

            if(event.getOption("group") != null){
                ICloudServiceGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName(event.getOption("group").getAsString());
                if(group == null){
                    hook.sendMessage("This group cannot be found!").queue();
                    return;
                }
                hook.sendMessage("```" + group.toString() + "```").queue();
                return;
            }

            hook.sendMessage("Please add an argument!").queue();
        }
    }
}
