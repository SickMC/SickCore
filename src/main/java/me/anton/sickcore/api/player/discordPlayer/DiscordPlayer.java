package me.anton.sickcore.api.player.discordPlayer;

import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.enums.Language;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class DiscordPlayer implements IDiscordPlayer{

    private IAPIPlayer player;
    public DiscordPlayer(IAPIPlayer player){
        this.player = player;
    }

    public DiscordPlayer(Member member) {
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
        return player;
    }
}
