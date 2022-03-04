package me.anton.sickcore.modules.discord.modules.ranks;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.api.player.bungeePlayer.BungeePlayer;
import me.anton.sickcore.api.utils.discord.DiscordIds;
import me.anton.sickcore.api.utils.discord.PrivateGuildIDs;
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

            switch (new APIPlayer(userID).getRank()){
                case DEV, CONTENT, BUILDER -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.staff)).queue();
                }
                case ADMIN -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.admin)).queue();
                }
                case MODERATOR -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mod)).queue();
                }
                case PLAYER -> {
                    module.getMainGuild().modifyMemberRoles(member, player).queue();
                }
                case VIP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.vip)).queue();
                }
                case MVP -> {
                    module.getMainGuild().modifyMemberRoles(member, player, module.getMainGuild().getRoleById(DiscordIds.mvp)).queue();
                }
            }
        });
        if (DiscordModule.getInstance().getSecondGuild().isMember(DiscordModule.getInstance().getJda().getUserById(userID)))
            module.getSecondGuild().retrieveMemberById(userID).queue(member -> {
                switch (new APIPlayer(userID).getRank()){
                    case BUILDER -> {
                        module.getSecondGuild().modifyMemberRoles(member, module.getSecondGuild().getRoleById(PrivateGuildIDs.builder)).queue();
                    }
                    case CONTENT -> {
                        module.getSecondGuild().modifyMemberRoles(member, module.getSecondGuild().getRoleById(PrivateGuildIDs.content)).queue();
                    }
                    case DEV -> {
                        module.getSecondGuild().modifyMemberRoles(member, module.getSecondGuild().getRoleById(PrivateGuildIDs.dev)).queue();
                    }
                    case MODERATOR -> {
                        module.getSecondGuild().modifyMemberRoles(member, module.getSecondGuild().getRoleById(PrivateGuildIDs.mod)).queue();
                    }
                    case ADMIN -> {
                        module.getSecondGuild().modifyMemberRoles(member, module.getSecondGuild().getRoleById(PrivateGuildIDs.admin)).queue();
                    }
                }
            });
    }

    public RankUpdate(BungeePlayer bungeePlayer){
        if (!bungeePlayer.api().isVerified())return;
        DiscordModule module = DiscordModule.getInstance();
        new RankUpdate(bungeePlayer.api().getDiscordID());
    }

}
