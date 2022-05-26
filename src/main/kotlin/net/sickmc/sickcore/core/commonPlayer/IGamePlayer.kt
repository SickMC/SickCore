package net.sickmc.sickcore.core.commonPlayer

import org.bson.Document

interface IGamePlayer {

    val sickPlayer: SickPlayer
    val gameDocument: Document

}