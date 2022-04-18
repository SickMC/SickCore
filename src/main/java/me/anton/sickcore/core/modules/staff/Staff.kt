package me.anton.sickcore.core.modules.staff

import me.anton.sickcore.core.player.SickPlayer
import me.anton.sickcore.utils.mongo.MongoCollection
import me.anton.sickcore.utils.mongo.MongoDocument
import java.util.UUID

class Staff {

    companion object{
        lateinit var instance: Staff
    }

    init {
        instance = this
    }

    var collection = MongoCollection("staff")

    suspend fun getPlayers(): List<StaffPlayer>{
        val players = ArrayList<StaffPlayer>()
        val playerNames = getOverview().document.getList("members", String::class.java)
        playerNames.forEach {
            StaffPlayers.getStaffPlayer(UUID.fromString(it))?.let { it1 -> players.add(it1) }
        }
        return players.toList()
    }

    suspend fun getOverview(): MongoDocument{
        return collection.getDocument("type", "overview")!!
    }

    suspend fun addMember(player: SickPlayer){
        StaffPlayers.createPlayer(player.uuid)
    }

}