package me.anton.sickcore.api.player.apiPlayer.enums;

import me.anton.sickcore.api.utils.common.string.EnumUtils;
import me.anton.sickcore.api.utils.common.string.StringUtils;

public enum Rank {

    ADMIN, DEV, MODERATOR, BUILDER, CONTENT, MVP, VIP, PLAYER;



    public String getPrefix(){
        return StringUtils.capitalize(EnumUtils.toName(this));
    }

    public String getFullPrefix(){
        String prefix = RankBridge.getColor(this) + getPrefix() + "§8 × §r";
        if (this == MVP)prefix = RankBridge.getColor(this) + getPrefix().toUpperCase() + "§8 × §r";
        if (this == VIP)prefix = RankBridge.getColor(this) + getPrefix().toUpperCase() + "§8 × §r";
        return prefix;
    }


}
