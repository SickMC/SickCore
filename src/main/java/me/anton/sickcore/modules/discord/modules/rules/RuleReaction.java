package me.anton.sickcore.modules.discord.modules.rules;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class RuleReaction extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (!event.getComponentId().equals("rule_accept"))return;
        Role player = DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.player);
        if (event.getMember().getRoles().contains(player)) {
            event.getMember().getRoles().forEach(role -> {
                DiscordModule.getInstance().getMainGuild().removeRoleFromMember(event.getMember(), role).queue();
                event.reply("Rules unaccepted!").setEphemeral(true).queue();
            });
            return;
        }
        event.getGuild().addRoleToMember(event.getMember(), player).queue();
        event.reply("Rules accepted!").setEphemeral(true).queue();
        return;
    }
}
