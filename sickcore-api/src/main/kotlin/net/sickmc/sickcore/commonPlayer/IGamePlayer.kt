package net.sickmc.sickcore.commonPlayer

import org.bson.Document

interface IGamePlayer {

    val sickPlayer: SickPlayer
    val gameDocument: Document

}