package me.anton.sickcore.modules.discord.modules.staffcommands.cloud;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import me.anton.sickcore.modules.discord.handlers.command.SelectionMenuHelper;
import me.anton.sickcore.modules.discord.handlers.command.SlashCommand;
import me.anton.sickcore.modules.discord.handlers.command.SlashSubCommand;
import me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CloudStopCommand extends SlashSubCommand {
    public CloudStopCommand(SlashCommand mainCommand) {
        super(mainCommand);
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stops services of the cloud!";
    }

    @Override
    public List<OptionData> initSubOptionData() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {
        List<ICloudService> services = CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects();

        SelectionMenu.Builder builder = SelectionMenu.create("cloud:stop")
                .setPlaceholder("Service")
                .setRequiredRange(1, services.size());

        services.forEach(service -> builder.addOption(service.getName(), service.getName()));

        SelectionMenuHelper helper = new SelectionMenuHelper(builder.build(), result -> {
            Message message = null;
            AtomicReference<String> content = new AtomicReference<>("");
            for (SelectOption selectOption : result) {
                String serviceName = selectOption.getLabel();
                ICloudService cloudService = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(serviceName);
                if (message == null) {
                    if (cloudService == null) {
                        content.set(serviceName + " cannot be found!");
                        message = hook.sendMessage(content.get()).complete();
                    } else {
                        content.set(serviceName + " is stopping!");
                        message = hook.sendMessage(content.get()).complete();
                        CloudAPI.getInstance().getCloudServiceManager().stopService(cloudService);
                    }
                } else {
                    if (cloudService == null) {
                        content.set(content.get() + "\n" + serviceName + " cannot be found!");
                        message.editMessage(content.get()).queue();
                        continue;
                    }
                    content.set(content.get() + "\n" + serviceName + " is stopping!");
                    message.editMessage(content.get()).queue();
                    CloudAPI.getInstance().getCloudServiceManager().stopService(cloudService);
                }
            }
        });

        hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Cloud Services").setContent("Choose your services!").build()).addActionRow(helper.getSelectionMenu()).queue();
    }
}
