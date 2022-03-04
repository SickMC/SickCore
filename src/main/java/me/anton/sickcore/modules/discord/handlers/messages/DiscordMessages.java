package me.anton.sickcore.modules.discord.handlers.messages;

import me.anton.sickcore.api.player.discordPlayer.DiscordPlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DiscordMessages {

    public static MessageEmbed getNoPermission(Member member){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(member)
                .setTitle("No Permission")
                .setContent("You don't have the permission to do this!");
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(member)
                .setTitle("Keine Rechte")
                .setContent("Du hast keine Rechte dafür!");

        DiscordPlayer player = new DiscordPlayer(member);
        return player.getEmbed(en.build(), de.build());
    }

    public static MessageEmbed getNotVerified(Member member){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(member)
                .setTitle("Nicht verifiziert")
                .setContent("Du bist nicht verifiziert!\nDu kannst deinen Account in "
                        + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.verifychannel).getAsMention() + " verifizeren!");

        return de.build();
    }

    public static MessageEmbed getNoStaff(Member member){
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder en = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(member)
                .setTitle("Staff Command")
                .setContent("This is a staff command!");
        me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder de = new me.anton.sickcore.modules.discord.handlers.messages.EmbedBuilder(member)
                .setTitle("Staff Command")
                .setContent("Das ist ein Staff Command!");

        DiscordPlayer player = new DiscordPlayer(member);
        return player.getEmbed(en.build(), de.build());
    }

    public static MessageEmbed getOnlyBotChat(Member member){
        EmbedBuilder embedBuilder = new EmbedBuilder(member)
                .setTitle("Wrong channel")
                .setContent("You can only use commands in " + DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.botchatchannel).getAsMention() + "!");
        return embedBuilder.build();
    }
}
