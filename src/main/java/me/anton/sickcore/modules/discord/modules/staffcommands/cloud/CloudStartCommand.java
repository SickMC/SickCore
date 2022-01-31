package me.anton.sickcore.modules.discord.modules.staffcommands.cloud;

import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.servicegroup.ICloudServiceGroup;
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

public class CloudStartCommand extends SlashSubCommand {
    public CloudStartCommand(SlashCommand mainCommand) {
        super(mainCommand);
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts services of the cloud";
    }


    @Override
    public List<OptionData> initSubOptionData() {
        return new ArrayList<>();
    }

    @Override
    public void execute(User user, InteractionHook hook, SlashCommandEvent event) {
        List<ICloudServiceGroup> groups = CloudAPI.getInstance().getCloudServiceGroupManager().getAllCachedObjects();

        SelectionMenu.Builder groupSelector = SelectionMenu.create("cloud:start:group")
                .setPlaceholder("Group")
                .setRequiredRange(1, groups.size());

        groups.forEach(group -> groupSelector.addOption(group.getName(), group.getName()));

        SelectionMenuHelper groupHelper = new SelectionMenuHelper(groupSelector.build(), groupResult -> {
            SelectionMenu.Builder countSelector = SelectionMenu.create("cloud:start:count")
                    .setPlaceholder("Anzahl")
                    .setRequiredRange(1, 1)
                    .addOption("1", "1")
                    .addOption("2", "2")
                    .addOption("3", "3")
                    .addOption("4", "4")
                    .addOption("5", "5")
                    .addOption("7", "7")
                    .addOption("10", "10");
            SelectionMenuHelper countHelper = new SelectionMenuHelper(countSelector.build(), countResult -> {
                int count = Integer.parseInt(countResult.get(0).getValue());
                Message message = null;
                AtomicReference<String> content = new AtomicReference<>("");
                for (SelectOption selectOption : groupResult) {
                    String groupName = selectOption.getValue();
                    ICloudServiceGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServiceGroupByName(groupName);
                    if(message == null) {
                        if (group == null) {
                            content.set("Group " + groupName + " cannot be found!");
                        } else {
                            content.set("Start " + count + " service/s of group " + groupName);
                            for (int i = 0; i < count; i++) {
                                CloudAPI.getInstance().getCloudServiceGroupManager().startNewService(group);
                            }
                        }
                        message = hook.sendMessage(content.get()).complete();
                    }else {
                        if (group == null) {
                            content.set(content.get() + "\nGroup " + groupName + " cannot be found!");
                        } else {
                            content.set(content.get() + "\nStart " + count + " service/s of group " + groupName);
                            for (int i = 0; i < count; i++) {
                                CloudAPI.getInstance().getCloudServiceGroupManager().startNewService(group);
                            }
                        }
                        message.editMessage(content.get()).queue();
                    }
                }
            });

            hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Cloud Services").setContent("Choose your amount of services!").build()).addActionRow(countHelper.getSelectionMenu()).queue();
        });

        hook.sendMessageEmbeds(new EmbedBuilder(user).setTitle("Cloud Services").setContent("Choose your group!").build()).addActionRow(groupHelper.getSelectionMenu()).queue();
    }
}
