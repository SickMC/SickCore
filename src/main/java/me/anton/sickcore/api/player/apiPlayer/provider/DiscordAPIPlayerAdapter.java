package me.anton.sickcore.api.player.apiPlayer.provider;

import com.mongodb.client.model.Filters;
import me.anton.sickcore.api.database.DatabaseModel;
import me.anton.sickcore.core.Core;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class DiscordAPIPlayerAdapter {

    private static DatabaseModel model = Core.getInstance().getPlayerModel();

    public static boolean isVerified(User user){
        return model.getCollection().find(Filters.eq("discordid", user.getId())).first()!= null;
    }

    public static boolean isVerified(Member user){
        return model.getCollection().find(Filters.eq("discordid", user.getId())).first()!= null;
    }

    public static boolean isVerified(String discordID){
        return model.getCollection().find(Filters.eq("discordid", discordID)).first()!= null;
    }

}
