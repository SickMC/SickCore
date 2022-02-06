package me.anton.sickcore.modules.discord.modules;

import me.anton.sickcore.modules.discord.modules.discordlog.DiscordLogModule;
import me.anton.sickcore.modules.discord.modules.leveling.LevelingModule;
import me.anton.sickcore.modules.discord.modules.lobby.LobbyModule;
import me.anton.sickcore.modules.discord.modules.managers.ManageModule;
import me.anton.sickcore.modules.discord.modules.ranks.RankModule;
import me.anton.sickcore.modules.discord.modules.rules.RuleModule;
import me.anton.sickcore.modules.discord.modules.staffcommands.StaffCommandModule;
import me.anton.sickcore.modules.discord.modules.ticket.TicketModule;
import me.anton.sickcore.modules.discord.modules.verify.VerifyModule;

import java.util.Arrays;
import java.util.List;

public class DiscordModuleHandler {

    private List<IDiscordModule> modules = Arrays.asList(new VerifyModule(), new RankModule(), new ManageModule(), new RuleModule(), new LobbyModule(), new DiscordLogModule(), new TicketModule(), new StaffCommandModule(), new LevelingModule());

    public void loadModules(){
        modules.forEach(IDiscordModule::load);
    }

    public void unLoadModules(){
        modules.forEach(IDiscordModule::unload);
    }

}
