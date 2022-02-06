package me.anton.sickcore.api.player.discordPlayer;

import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.api.player.apiPlayer.APIPlayer;
import me.anton.sickcore.api.player.apiPlayer.IAPIPlayer;
import me.anton.sickcore.api.player.apiPlayer.language.Language;
import me.anton.sickcore.api.player.apiPlayer.provider.DiscordAPIPlayerAdapter;
import me.anton.sickcore.modules.discord.DiscordModule;
import me.anton.sickcore.modules.discord.modules.leveling.DiscordLevel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

public class DiscordPlayer implements IDiscordPlayer{

    private final IAPIPlayer player;
    private final Member member;

    public DiscordPlayer(Member member) {
        this.member = member;
        this.player = new APIPlayer(member.getUser().getId());
    }

    @Override
    public MessageEmbed getEmbed(MessageEmbed en, MessageEmbed de) {
        if (player.getLanguage() == Language.DEUTSCHDE)return de;
        else return en;
    }

    @Override
    public String getMessage(String en, String de) {
        if (player.getLanguage() == Language.DEUTSCHDE)return de;
        else return en;
    }

    @Override
    public IAPIPlayer api() {
        if (!DiscordAPIPlayerAdapter.isVerified(member))return null;
        return player;
    }

    @Override
    public DiscordLevel getLevel() {
        if (!DiscordAPIPlayerAdapter.isVerified(member))return null;
        DiscordLevel level = null;
        DatabaseModel model = DiscordModule.getInstance().getGamePlayer();
        Document leveldoc = model.getDocument("userID", member.getId());
        int points = leveldoc.getInteger("points");
        for (DiscordLevel value : DiscordLevel.values()) {
            if (points >= value.getStart() && points <= value.getEnd())level = value;
        }
        return level;
    }

    @Override
    public int getPoints(){
        if (!DiscordAPIPlayerAdapter.isVerified(member))return 0;
        DatabaseModel model = DiscordModule.getInstance().getGamePlayer();
        Document leveldoc = model.getDocument("userID", member.getId());
        return leveldoc.getInteger("points");
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
