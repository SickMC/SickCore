package me.anton.sickcore.api.player.discordPlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.modules.discord.DiscordModule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

public class DiscordPlayer implements IDiscordPlayer{

    private IAPIPlayer player;
    private Member member;

    public DiscordPlayer(IAPIPlayer player){
        if (!player.isVerified())return;
        this.player = player;
        this.member = DiscordModule.getInstance().getMainGuild().getMemberById(player.getDiscordID());
    }

    public DiscordPlayer(Member member) {
        this.member = member;
        this.player = new APIPlayer(member.getId());
    }

    @Override
    public MessageEmbed getEmbed(MessageEmbed en, MessageEmbed de) {
        if (player.getLanguage() == Language.DEUTSCH)return de;
        else return en;
    }

    @Override
    public String getMessage(String en, String de) {
        if (player.getLanguage() == Language.DEUTSCH)return de;
        else return en;
    }

    @Override
    public IAPIPlayer api() {
        if (!DiscordAPIPlayerAdapter.isVerified(member))return null;
        return player;
    }

    @Override
    public User user() {
        return member.getUser();
    }

    @Override
    public Member member() {
        return member;
    }
}
