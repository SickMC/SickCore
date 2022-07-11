package net.sickmc.sickcore.games.lobby

import net.sickmc.sickcore.commonPlayer.IGamePlayer
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.Cache
import net.sickmc.sickcore.utils.mongo.lobbyColl
import net.sickmc.sickcore.utils.mongo.retrieveOne
import org.bson.Document
import java.util.UUID

class LobbyPlayer(override val sickPlayer: SickPlayer, override val gameDocument: Document, ) : IGamePlayer{

    val gadgets = gameDocument.getList("gadgets", String::class.java).map { Gadget.valueOf(it) }

}

class LobbyPlayers : Cache<UUID, LobbyPlayer>, HashMap<UUID, LobbyPlayer>() {

    companion object {
        lateinit var instance: LobbyPlayers
    }

    init {
        instance = this
    }

    override fun getCachedEntity(entity: UUID): LobbyPlayer? {
        return this[entity]
    }

    override suspend fun getEntity(entity: UUID): LobbyPlayer {
        val doc = lobbyColl.retrieveOne("uuid", entity.toString()) ?: return createEntity(entity)
        return LobbyPlayer(SickPlayers.instance.getEntity(entity)!!, doc)
    }

    override suspend fun reloadEntity(entity: UUID): LobbyPlayer? {
        this[entity] = getEntity(entity)
        return this[entity]!!
    }

    override suspend fun createEntity(entity: UUID): LobbyPlayer {
        val playerDoc = Document("uuid", entity.toString())
            .append("gadgets", listOf<String>())
            .append("statistics", Document())
        lobbyColl.insertOne(playerDoc)
        return LobbyPlayer(SickPlayers.instance.getEntity(entity)!!, playerDoc)
    }


}