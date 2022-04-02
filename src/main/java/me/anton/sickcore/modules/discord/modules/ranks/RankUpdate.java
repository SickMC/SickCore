package me.anton.sickcore.modules.discord.modules.ranks;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Role;

public class RankUpdate {

    public RankUpdate(String userID){
        DiscordModule module = DiscordModule.getInstance();
        if (!DiscordAPIPlayerAdapter.isVerified(userID)){
            module.getMainGuild().retrieveMemberById(userID).queue(member -> module.getMainGuild().modifyMemberRoles(member, module.getMainGuild().getRoleById(DiscordIds.player)).queue());
            if (DiscordModule.getInstance().getSecondGuild().isMember(DiscordModule.getInstance().getJda().getUserById(userID)))
                module.getSecondGuild().retrieveMemberById(userID).queue(member -> member.getRoles().forEach(role -> module.getSecondGuild().removeRoleFromMember(member, role)));
        }
        module.getMainGuild().retrieveMemberById(userID).queue(member -> {
            Role player = module.getMainGuild().getRoleById(DiscordIds.player);

            module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(new APIPlayer(userID).getRank().getParent().getDiscordID())).queue();
        });

        if (DiscordModule.getInstance().getSecondGuild().isMember(DiscordModule.getInstance().getJda().getUserById(userID)) && new APIPlayer(userID).isTeam())
            module.getSecondGuild().retrieveMemberById(userID).queue(member -> {
                module.getMainGuild().modifyMemberRoles(member, module.getMainGuild().getRoleById(new APIPlayer(userID).getRank().getPrivateDiscordRoleID())).queue();
            });
    }

    public RankUpdate(BungeePlayer bungeePlayer){
        if (!bungeePlayer.api().isVerified())return;
        DiscordModule module = DiscordModule.getInstance();
        new RankUpdate(bungeePlayer.api().getDiscordID());
    }

}
