package me.anton.sickcore.modules.discord.modules.rules;

import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RuleMessage {

    public RuleMessage(){
        if (!DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.rulesChannel).hasLatestMessage())return;
        EmbedBuilder discord = new EmbedBuilder()
                .setTitle("**Discord Regeln**")
                .setColor(Color.ORANGE)
                .setDescription("**Verhalten**\n" +
                        "Textchats:\n" +
                        "- Bitte nur auf Deutsch oder Englisch schreiben!\n" +
                        "- Spam ist zu unterlassen\n" +
                        "- Keine übermäßige Benutzung von Caps oder Emotes\n" +
                        "\n" +
                        "Sprachchats:\n" +
                        "- Störgeräusche sind zu unterlassen\n" +
                        "- Sprachverzehrer sind nicht erlaubt\n" +
                        "- Channel-hopping (ständiges Wechseln der Kanäle) ist zu unterlassen\n" +
                        "- Das Aufnehmen von anderen ohne deren Erlaubnis ist nicht erlaubt!\n" +
                        "\n" +
                        "Allgemein:\n" +
                        "- Beleidigungen sind sowohl in Sprachchats und Textchats verboten, sollte euch jemand in einem Sprachchannel beleidigen meldet dies einem der Mods\n" +
                        "- Rassismus oder Antisemitismus führen zu einem sofortigen Ban ohne Chance wieder entbannt zu werden!\n" +
                        "\n" +
                        "Datenschutz:\n" +
                        "- Die Weitergabe von persönlichen Daten wie z.B Telefonnummer, Email-Adresse oder ähnlichem ist untersagt\n" +
                        "\n" +
                        "**Bestrafungen**\n" +
                        "- der mehrfache Verstoß gegen die Serverregeln führt zu einem permanenten Bann\n" +
                        "- Falls du der Meinung bist, dass du zu Unrecht gebannt oder gekickt wurdest melde dich bei einem der Moderatoren per DM (DirectMessage)\n" +
                        "\n" +
                        "Nickname\n" +
                        "- Nicknames dürfen keine beleidigenden oder andere verbotenen oder geschützte Namen oder Namensteile enthalten.\n" +
                        "- Der Nickname darf nicht aus Sonderzeichen bestehen.")
                .setFooter("SickMC • Requested by SickMC", DiscordModule.getInstance().getJda().getSelfUser().getEffectiveAvatarUrl());

        EmbedBuilder server = new EmbedBuilder()
                .setTitle("**Server Regeln**")
                .setColor(Color.ORANGE)
                .setDescription("**Allgemein**\n" +
                        "- es gelten dieselben Regeln wie in den Discord-Regeln:\n" +
                        "- keine Beleidigungen, rassistische Aussagen, usw.\n" +
                        "- Hacking/Cheating ist strengstens untersagt und wird mit einem Bann bestraft\n" +
                        "\n" +
                        "**Survival**\n" +
                        "- Griefen ist verboten und wird mit einem Warn bestraft\n" +
                        "- Farmen dürfen nur unter Einverständnis der Besitzer benutzt werden - dieses Vergehen wird mit einem Bann bestraft\n" +
                        "- drei Warns führen zu einem permanenten Bann vom Survival-Server")
                .setFooter("SickMC • Requested by SickMC", DiscordModule.getInstance().getJda().getSelfUser().getEffectiveAvatarUrl());

        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.rulesChannel).sendMessageEmbeds(discord.build(), server.build()).queue();
        DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.rulesChannel).addReactionById(DiscordModule.getInstance().getMainGuild().getTextChannelById(DiscordIds.rulesChannel).getLatestMessageId(), "U+2705").queueAfter(5, TimeUnit.SECONDS);
    }

}
