package me.anton.sickcore.modules.punishment;

import me.anton.sickcore.api.player.bukkitPlayer.IBukkitPlayer;
import net.kyori.adventure.text.Component;

public class PunishmentMessages {

    public static Component paperKick(String reasonen, String reasonde, IBukkitPlayer player){
        Component en = Component.text("§6SickMC" +
                "\n§7Network-Kick\nReason: §6" + reasonen);
        Component de = Component.text("§6SickMC" +
                "\n§7Netzwerk-Kick\nGrund: §6" + reasonde);

        return (Component) player.api().languageObject(en, de);
    }

}
