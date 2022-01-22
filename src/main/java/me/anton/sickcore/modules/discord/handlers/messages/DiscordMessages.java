package me.anton.sickcore.modules.discord.handlers.messages;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class DiscordMessages {

    public static MessageEmbed getNoPermission(Member member){
        EmbedBuilder en = new EmbedBuilder()
                .setTitle("**No Permission**")
                .setDescription("You don't have the permission to do this!")
                .setColor(Color.ORANGE)
                .setFooter("SickMC • Requested by " + member.getEffectiveName());

        EmbedBuilder de = new EmbedBuilder()
                .setTitle("**Keine Rechte**")
                .setDescription("Du hast keine Rechte dafür!")
                .setColor(Color.ORANGE)
                .setFooter("SickMC • Requested by " + member.getEffectiveName());
        if (DiscordAPIPlayerAdapter.isVerified(member)) {
            if (new APIPlayer(member.getId()).getLanguage() == Language.DEUTSCH)
                return de.build();
            if (new APIPlayer(member.getId()).getLanguage() == Language.ENGLISCH)
                return en.build();
        }
        return de.build();
    }

    public static MessageEmbed getNotVerified(Member member){
        EmbedBuilder en = new EmbedBuilder()
                .setTitle("**Not Verified**")
                .setDescription("You are not verified!\nYou can verify your account in "
                        + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.verifychannel).getAsMention() + "!")
                .setColor(Color.ORANGE)
                .setFooter("SickMC • Requested by " + member.getEffectiveName());

        EmbedBuilder de = new EmbedBuilder()
                .setTitle("**Nicht verifiziert**")
                .setDescription("Du bist nicht verifiziert!\nDu kannst deinen Account in "
                        + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.verifychannel).getAsMention() + " verifizeren!")
                .setColor(Color.ORANGE)
                .setFooter("SickMC • Requested by " + member.getEffectiveName());
        if (DiscordAPIPlayerAdapter.isVerified(member)) {
            if (new APIPlayer(member.getId()).getLanguage() == Language.DEUTSCH)
                return de.build();
            if (new APIPlayer(member.getId()).getLanguage() == Language.ENGLISCH)
                return en.build();
        }
        return de.build();
    }

    public static String getFooter(User user){
        return "SickMC • Requested by " + user.getName();
    }

    public static String getAvatarURL(User user){ return user.getEffectiveAvatarUrl();}

}
