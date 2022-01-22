package me.anton.sickcore.modules.discord.handlers.command;

import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlashCommandBuilder extends ListenerAdapter {

    private HashMap<String, SlashCommand> commands = new HashMap<>();
    private HashMap<String, SlashCommand> aliases = new HashMap<>();

    public void registerCommand(SlashCommand command){
        commands.put(command.getName(), command);
        command.getAliases().forEach(s -> aliases.put(s, command));

        CommandCreateAction action = DiscordModule.getInstance().getJda()
                .upsertCommand(command.getName(), command.getDescription());
        if(command.getSlashSubCommands().isEmpty()){
            action.addOptions(command.getOptionData());
        }else {
            action.addSubcommands(command.getSlashSubCommandData());
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
        if(called.get()) return;
        aliases.forEach((alias, command) -> {
            if(called.get()) return;
            if(event.getName().equalsIgnoreCase(alias)){
                event.deferReply().complete();
                command.preExecute(event.getUser(), event.getHook(), event);
                called.set(true);
            }
        });
    }
}