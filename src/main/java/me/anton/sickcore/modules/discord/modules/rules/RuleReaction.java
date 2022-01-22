package me.anton.sickcore.modules.discord.modules.rules;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

public class RuleReaction extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getTextChannel().getId().equals(DiscordIds.rulesChannel))return;
        event.getMessage().editMessageComponents(ActionRow.of(Button.success("rule_accept", Emoji.fromUnicode("U+2705")))).queue();
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (!event.getComponentId().equals("rule_accept"))return;
        if (event.getMember().getRoles().contains(DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.player))) {
            event.getMember().getRoles().forEach(role -> {
                DiscordModule.getInstance().getMainGuild().removeRoleFromMember(event.getMember(), role).queue();
            });
        }
        event.getGuild().addRoleToMember(event.getMember(), DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.player)).queue();
    }
}
