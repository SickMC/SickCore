package net.sickmc.sickcore.core.player

import net.sickmc.sickcore.utils.PlayerUtils
import net.sickmc.sickcore.utils.mongo.MongoCollection
import org.bson.Document
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SickPlayers {

    companion object{
        val collection = MongoCollection("sickPlayers")
        val players = HashMap<UUID, SickPlayer>()

        suspend fun getSickPlayer(uuid: UUID): SickPlayer?{
            if (players.contains(uuid))return players[uuid]
            else{
                if (collection.getDocument("uuid", uuid.toString()) == null)return null
                val sickPlayer = SickPlayer(uuid, collection.getDocument("uuid", uuid.toString())!!)
                players[uuid] = sickPlayer
                return sickPlayer
            }
        }

        suspend fun reloadPlayer(uuid: UUID): SickPlayer?{
            if (players.contains(uuid)) players.remove(uuid)
            return getSickPlayer(uuid)
        }

        suspend fun createPlayer(uuid: UUID): SickPlayer{
            if (collection.getDocument("uuid", uuid.toString()) != null)return getSickPlayer(uuid)!!
            val playerDoc = Document("uuid", uuid.toString())
                .append("language", "english_uk")
                .append("discordID", "0")
                .append("name", PlayerUtils.fetchName(uuid))
                .append("addiction", 0)
                .append("exp", 0)
                .append("extraPermissions", ArrayList<String>())
                .append("rank", "Player")
                .append("rankExpire", "none")
                .append("permanentRank", "Player")
                .append("mates", Document())
                .append("bubble", Document())
                .append("achievments", Document())
                .append("firstJoin", System.currentTimeMillis())
                .append("playtime", 0L)
            collection.createDocument(playerDoc)
            return getSickPlayer(uuid)!!
        }
    }

}