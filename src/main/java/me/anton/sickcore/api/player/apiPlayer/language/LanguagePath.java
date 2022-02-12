package me.anton.sickcore.api.player.apiPlayer.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LanguagePath {

    NETWORK_COMMAND_NOPERMISSION("network.command.noPermission"),
    NETWORK_COMMAND_NOSTAFF("network.command.noStaff"),
    NETWORK_COMMAND_NOMOD("network.command.noMod"),
    PAPER_COMMAND_LAGCOMMAND_TIMINGS("paper.command.lagCommand.timings"),
    PAPER_COMMAND_LOBBY_MODI_MODINPCHANDLER_SERVERNOTAVAILABLE("paper.command.lobby.modi.ModiNPCHandler.serverNotAvailable"),
    PAPER_COMMAND_LOBBY_MODI_MODINPCHANDLER_TELEPORTSUCCESS("paper.command.lobby.modi.ModiNPCHandler.teleportSuccess"),
    NETWORK_AVAILABLE_WITHMVPORHIGHER("network.available.withmvporhigher"),
    NETWORK_AVAILABLE_WITHVIPPORHIGHER("network.available.withviporhigher"),
    PAPER_COMMAND_LOBBY_PROFILEINVENTORY_CHOOSE("paper.command.lobby.rankinventory.choose"),
    PAPER_COMMAND_LOBBY_RANKINVENTORY_LANGUAGE_PICK("paper.command.lobby.rankinventory.language.pick"),
    PAPER_COMMAND_LOBBY_RANKINVENTORY_RESETRANKCOLOR("paper.command.lobby.rankinventory.resetrankcolor"),
    PAPER_COMMAND_LOBBY_RANKINVENTORY_RESETRANKCOLORDESCRIPTION("paper.command.lobby.rankinventory.resetrankcolor_description"),
    PAPER_COMMAND_LOBBY_RANKINVENTORY_PICKCOLOR("paper.command.lobby.rankinventory.color.pick"),
    PAPER_LOBBY_LOBBYITEMS_PROFILE_DESCRIPTION("paper.lobby.lobbyitems.profile.description"),
    PROXY_STAFF_COMMAND_BUILDSERVER_ALREADY("proxy.staff.command.buildserver.already"),
    PROXY_STAFF_COMMAND_BUILDSERVER_SUCCESS("proxy.staff.command.buildserver.success"),
    PROXY_STAFF_COMMAND_LOBBY_ALREADY("proxy.staff.command.lobby.already"),
    PROXY_STAFF_COMMAND_LOBBY_SUCCESS("proxy.staff.command.lobby.success"),
    PROXY_COMMAND_PLAYTIME_TIME("proxy.command.playtime.time"),
    PROXY_SERVICE_RESTART_WARNING("proxy.service.restart.warning"),
    PROXY_SERVICE_RESTART_NOW("proxy.service.restart.warning.seconds"),
    NETWORK_COMMAND_NOPLAYER("network.command.noPlayer");

    public String path;
}
