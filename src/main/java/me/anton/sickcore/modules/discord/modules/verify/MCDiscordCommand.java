package me.anton.sickcore.modules.discord.modules.verify;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.database.Finder;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.common.MathUtils;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.api.utils.minecraft.messages.ConsoleMessages;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.discordlog.DiscordLogModule;
import me.anton.sickcore.modules.discord.modules.ranks.RankUpdate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.Document;

import java.awt.*;
import java.time.Instant;

@CommandAlias("discord|dc")
public class MCDiscordCommand extends BaseCommand {

    @Subcommand("verify")
    @Description("Verify with discord account")
    public void onCmd(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        BungeePlayer player = new BungeePlayer(sender);

        if (player.api().isVerified()){player.getPlayer().sendMessage(new TextComponent((String) player.api().languageObject("§7You are already verified with §6" + DiscordModule.getInstance().getJda().getUserById(player.api().getDiscordID()).getAsTag() + "§7!", "§7Du bist bereits mit §6" + DiscordModule.getInstance().getJda().getUserById(player.api().getDiscordID()).getAsTag() + "§7 verbunden!")));return;}

        int verifyCode = MathUtils.getRandomNumberInRange(100000, 999999);
        if (VerifyModule.getVerifyModule().getVerifyList().containsValue(verifyCode))verifyCode = MathUtils.getRandomNumberInRange(100000, 999999);

        VerifyModule.getVerifyModule().getVerifyList().put(player, verifyCode);
        VerifyModule.getVerifyModule().getVerifyListReturn().put(verifyCode, player);

        BaseComponent en = new TextComponent("§7Your verify code is §6" + verifyCode + "§7!");
        en.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(verifyCode)));
        en.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to copy the code!").create()));

        BaseComponent de = new TextComponent("§7Dein Verifikationscode ist §6" + verifyCode + "§7!");
        en.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(verifyCode)));
        en.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke um den Code zu kopieren!").create()));

        player.getPlayer().sendMessage((BaseComponent) player.api().languageObject(en, de));
        MessageEmbed embed = new EmbedBuilder()
                .setTimestamp(Instant.now())
                .setTitle("**Verify | SickMC**")
                .setDescription(player.api().getName() +" started verification! \nHis code is " + verifyCode +"!")
                .setFooter("SickMC • Requested by " + player.api().getName())
                .setColor(Color.ORANGE).build();
        DiscordLogModule.getInstance().log(embed);
    }

    @Subcommand("delete")
    @Description("Deletes your connection with the discord account")
    public void onCMd(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }
        DatabaseModel model = DiscordModule.getInstance().getGamePlayer();

        BungeePlayer player = new BungeePlayer(sender);

        if (!player.api().isVerified()){player.getPlayer().sendMessage(new TextComponent((String) player.api().languageObject("§4You are not verifed!", "§4Du bist nicht verifiziert!")));return;}
        Document playermodel = model.getDocument(Finder.stringFinder("userID", player.api().getDiscordID()));
        playermodel.replace("enabled", false);
        model.updateDocument(Finder.stringFinder("userID", player.api().getDiscordID()), playermodel);

        DiscordModule.getInstance().getMainGuild().retrieveMemberById(player.api().getDiscordID()).queue(member -> DiscordModule.getInstance().getMainGuild().modifyMemberRoles(member, DiscordModule.getInstance().getMainGuild().getRoleById(DiscordIds.player)).queue());
        player.api().setDiscordID("0");

        player.getPlayer().sendMessage(new TextComponent((String) player.api().languageObject("§7Your discord account is now §6unverified§7!", "§7Dein Discord Account ist nun §6unverifiziert§7!")));
    }

    @Subcommand("update")
    @Description("Updates your rank on discord")
    public void onCMD(CommandSender sender){
        if (!(sender instanceof ProxiedPlayer)){
            ConsoleMessages.noPlayerBungee(sender);
            return;
        }

        BungeePlayer player = new BungeePlayer(sender);

        if (!player.api().isVerified()){player.getPlayer().sendMessage(new TextComponent((String) player.api().languageObject("§4You are not verifed!", "§4Du bist nicht verifiziert!")));return;}

        new RankUpdate(player);

        player.getPlayer().sendMessage(new TextComponent((String) player.api().languageObject("§7Your rank successfully updated!", "§7Dein Rang wurde erfolgreich neu geladen!")));

    }

    @HelpCommand
    public void onHelp(CommandHelp help){help.showHelp();}

}
