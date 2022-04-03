package me.anton.sickcore.modules.discord.handlers.command;

import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.requests.restaction.CommandEditAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlashCommandBuilder extends ListenerAdapter {

    private HashMap<String, SlashCommand> commands = new HashMap<>();

    public void registerCommand(SlashCommand command){
        commands.put(command.getName(), command);
        if(DiscordModule.getInstance().getCommands().containsKey(command.getName())){
            CommandEditAction action =
                    DiscordModule.getInstance().getJda()
                            .editCommandById(DiscordModule.getInstance().getCommands().getString(command.getName()))
                            .setDescription(command.getDescription());
            if(command.getSlashSubCommands().isEmpty()){
                action
                        .clearOptions()
                        .addOptions(command.getOptionData());
            }else {
                action
                        .addSubcommands(command.getSlashSubCommandData());
            }
            action.queue();
        }else {
            CommandCreateAction action = DiscordModule.getInstance().getJda()
                    .upsertCommand(command.getName(), command.getDescription());
            if(command.getSlashSubCommands().isEmpty()){
                action
                        .addOptions(command.getOptionData());
            }else {
                action
                        .addSubcommands(command.getSlashSubCommandData());
            }

            action.queue(regCommand -> {
                Logger.info(regCommand.getName() + ": " + regCommand.getId(), this.getClass());
            });
        }
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        AtomicBoolean called = new AtomicBoolean(false);
        commands.forEach((name, command) -> {
            if(called.get()) return;
            if(event.getName().equalsIgnoreCase(name)){
                event.deferReply().complete();
                command.preExecute(event.getUser(), event.getHook(), event);
                called.set(true);
            }
        });
    }
}