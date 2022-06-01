package net.sickmc.sickcore.staff

import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.ISickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.mongo.staffColl
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap

class StaffPlayer(override val uniqueID: UUID, override val document: Document) : ISickPlayer {

    val notifyConfig = document.get("notify", Document::class.java)
    val joined = document.getLong("joined")
    val cooldown = document.getLong("cooldown")
    val warns = document.getInteger("warns")

    suspend fun getSickPlayer(): SickPlayer{
        return SickPlayers.instance.getEntity(uniqueID)
    }

}

class StaffPlayers{

    companion object{
        val players = HashMap<UUID, StaffPlayer>()
        init {
            databaseScope.launch {
                staffColl.find().toList().forEach{
                    if (it.containsKey("type"))return@forEach
                    players[UUID.fromString(it.getString("uuid"))] = StaffPlayer(UUID.fromString(it.getString("rank")), it)
                }
            }
        }
        fun getCachedStaffPlayer(uuid: UUID): StaffPlayer {
            return players[uuid] ?: error("StaffPlayer $uuid cannot be found")
        }
        suspend fun getStaffPlayer(uuid: UUID): StaffPlayer?{
            if (players.contains(uuid))return players[uuid]
            else{
                if (staffColl.retrieveOne("uuid", uuid.toString()) == null)return null
                val sickPlayer = StaffPlayer(uuid, staffColl.retrieveOne("uuid", uuid.toString())!!)
                players[uuid] = sickPlayer
                return sickPlayer
            }
        }

        suspend fun reloadPlayer(uuid: UUID): StaffPlayer?{
            if (players.contains(uuid)) players.remove(uuid)
            return getStaffPlayer(uuid)
        }

        suspend fun createPlayer(uuid: UUID): StaffPlayer {
            if (staffColl.retrieveOne("uuid", uuid.toString()) != null)return getStaffPlayer(uuid)!!
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
            staffColl.insertOne(playerDoc)
            return getStaffPlayer(uuid)!!
        }
    }

}