package me.anton.sickcore.modules.discord.modules.ranks;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.bungeePlayer.IBungeePlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Role;

public class RankUpdate {

    public RankUpdate(String userID){
        DiscordModule module = DiscordModule.getInstance();
        if (!DiscordAPIPlayerAdapter.isVerified(userID))module.getMainGuild().retrieveMemberById(userID).queue(member -> module.getMainGuild().modifyMemberRoles(member, module.getMainGuild().getRoleById(DiscordIds.player)).queue());
        module.getMainGuild().retrieveMemberById(userID).queue(member -> {
            Role player = module.getMainGuild().getRoleById(DiscordIds.player);

            switch (new APIPlayer(userID).getRank()){
                case DEV -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.dev)).queue();
                    break;
                }
                case ADMIN -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.admin)).queue();
                    break;
                }
                case MODERATOR -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mod)).queue();
                    break;
                }
                case BUILDER -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.builder)).queue();
                    break;
                }
                case CONTENT -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.content)).queue();
                    break;
                }
                case PLAYER -> {
                    module.getMainGuild().modifyMemberRoles(member, player).queue();
                    break;
                }
                case VIP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.vip)).queue();
                    break;
                }
                case MVP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mvp)).queue();
                    break;
                }
            }
        });
    }

    public RankUpdate(IBungeePlayer bungeePlayer){
        if (!bungeePlayer.api().isVerified())return;
        DiscordModule module = DiscordModule.getInstance();
        module.getMainGuild().retrieveMemberById(bungeePlayer.api().getDiscordID()).queue(member -> {
            Role player = module.getMainGuild().getRoleById(DiscordIds.player);

            switch (bungeePlayer.api().getRank()){
                case DEV -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.dev)).queue();
                    break;
                }
                case ADMIN -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.admin)).queue();
                    break;
                }
                case MODERATOR -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mod)).queue();
                    break;
                }
                case BUILDER -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.builder)).queue();
                    break;
                }
                case CONTENT -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.content)).queue();
                    break;
                }
                case PLAYER -> {
                    module.getMainGuild().modifyMemberRoles(member, player).queue();
                    break;
                }
                case VIP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.vip)).queue();
                    break;
                }
                case MVP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mvp)).queue();
                    break;
                }
            }
        });
    }

}
