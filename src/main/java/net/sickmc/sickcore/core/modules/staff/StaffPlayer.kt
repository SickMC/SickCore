package net.sickmc.sickcore.core.modules.staff

import net.sickmc.sickcore.core.player.SickPlayer
import net.sickmc.sickcore.core.player.SickPlayers
import net.sickmc.sickcore.utils.mongo.MongoCollection
import net.sickmc.sickcore.utils.mongo.MongoDocument
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap

class StaffPlayer(val uuid: UUID,val document: MongoDocument) {

    val notifyConfig = document.document.get("notify", Document::class.java)
    val joined = document.document.getLong("joined")
    val cooldown = document.document.getLong("cooldown")
    val warns = document.document.getInteger("warns")

    suspend fun getSickPlayer(): SickPlayer{
        return SickPlayers.getSickPlayer(uuid)!!
    }

}

class StaffPlayers{

    companion object{
        val collection = MongoCollection("staff")
        val players = HashMap<UUID, StaffPlayer>()

        suspend fun getStaffPlayer(uuid: UUID): StaffPlayer?{
            if (players.contains(uuid))return players[uuid]
            else{
                if (collection.getDocument("uuid", uuid.toString()) == null)return null
                val sickPlayer = StaffPlayer(uuid, collection.getDocument("uuid", uuid.toString())!!)
                players[uuid] = sickPlayer
                return sickPlayer
            }
        }

        suspend fun reloadPlayer(uuid: UUID): StaffPlayer?{
            if (players.contains(uuid)) players.remove(uuid)
            return getStaffPlayer(uuid)
        }

        suspend fun createPlayer(uuid: UUID): StaffPlayer {
            if (collection.getDocument("uuid", uuid.toString()) != null)return getStaffPlayer(uuid)!!
            val playerDoc = Document("uuid", uuid.toString())
                .append("joined", System.currentTimeMillis())
                .append("warns", 0)
                .append("notify", Document("service", "false")
                    .append("flags", "false")
                    .append("joininfo", "false")
                    .append("punishments", "false")
                    .append("report", "false")
                    .append("teamchat", "false"))
                .append("cooldown", 0L)
            collection.createDocument(playerDoc)
            return getStaffPlayer(uuid)!!
        }
    }

}