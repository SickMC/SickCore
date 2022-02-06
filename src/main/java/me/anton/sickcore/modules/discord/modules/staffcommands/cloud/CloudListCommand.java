package me.anton.sickcore.modules.discord.modules.staffcommands.cloud;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
import eu.thesimplecloud.api.wrapper.IWrapperInfo;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.common.math.MathUtils;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CloudListCommand extends SlashSubCommand {
    public CloudListCommand(SlashCommand mainCommand) {
        super(mainCommand);
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all running services!";
    }

    @Override
    public List<OptionData> initSubOptionData() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, IDiscordPlayer player, InteractionHook hook, SlashCommandEvent event) {
        if (!getMainCommand().isStaffChannel(event))return;
        StringBuilder wrapperBuilder = new StringBuilder();
        for (IWrapperInfo wrapperInfo : CloudAPI.getInstance().getWrapperManager().getAllCachedObjects()) {
            if(!wrapperBuilder.toString().isEmpty()) wrapperBuilder.append("\n");
            wrapperBuilder.append("> ").append(wrapperInfo.getName()).append(" (").append(wrapperInfo.getHost()).append(" | ").append(wrapperInfo.getUsedMemory()).append("/").append(wrapperInfo.getMaxMemory()).append("MB | ").append(MathUtils.roundToHalf(wrapperInfo.getCpuUsage())).append("%)");
        }

        StringBuilder groupsBuilder = new StringBuilder();
        for (ICloudServiceGroup group : CloudAPI.getInstance().getCloudServiceGroupManager().getAllCachedObjects()) {
            StringBuilder serviceBuilder = new StringBuilder();
            if(group.getOnlineServiceCount() == 0) continue;
            for (ICloudService service : group.getAllServices()) {
                if(!serviceBuilder.toString().isEmpty()) serviceBuilder.append("\n");
                serviceBuilder.append("> ").append(service.getName()).append(" (").append(service.getOnlineCount()).append("/").append(service.getMaxPlayers()).append(" | ").append(service.getMaxMemory()).append("MB)");
            }
            if(!groupsBuilder.toString().isEmpty()){
                groupsBuilder.append("\n").append("\n");
            }
            groupsBuilder.append(serviceBuilder.toString());
        }

        EmbedBuilder builder = new EmbedBuilder(user).setTitle("Cloud Services")
                .addField("Wrapper (" + CloudAPI.getInstance().getWrapperManager().getAllCachedObjects().size() + ")", wrapperBuilder.toString(), true)
                .addField(null).addField(null)
                .addField("Services (" + CloudAPI.getInstance().getCloudServiceGroupManager().getAllCachedObjects().size() + ")", groupsBuilder.toString(), true);

        hook.sendMessageEmbeds(builder.build()).queue();
    }
}
