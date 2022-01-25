package me.anton.sickcore.modules.discord.handlers.messages;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.player.discordPlayer.IDiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class DiscordMessages {

    public static MessageEmbed getNoPermission(Member member){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(EmbedType.UTILITY, member)
                .setTitle("**No Permission**")
                .setContent("You don't have the permission to do this!");
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(EmbedType.UTILITY, member)
                .setTitle("**Keine Rechte**")
                .setContent("Du hast keine Rechte dafür!");

        IDiscordPlayer player = new DiscordPlayer(member);
        return player.getEmbed(en.build(), de.build());
    }

    public static MessageEmbed getNotVerified(Member member){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(EmbedType.UTILITY, member)
                .setTitle("**Not Verified**")
                .setContent("You are not verified!\nYou can verify your account in "
                        + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.verifychannel).getAsMention() + "!");
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(EmbedType.UTILITY, member)
                .setTitle("**Nicht verifiziert**")
                .setContent("Du bist nicht verifiziert!\nDu kannst deinen Account in "
                        + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.verifychannel).getAsMention() + " verifizeren!");

        IDiscordPlayer player = new DiscordPlayer(member);
        return player.getEmbed(en.build(), de.build());
    }
}
