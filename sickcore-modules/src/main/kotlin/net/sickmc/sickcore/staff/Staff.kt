package net.sickmc.sickcore.staff

import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.mongo.staffColl
import org.bson.Document
import java.util.UUID

class Staff {

    companion object{
        lateinit var instance: Staff
    }

    init {
        instance = this
    }

    var overview: Document? = null

    fun getStaffPlayers(): List<StaffPlayer>{
        val players = ArrayList<StaffPlayer>()
        val playerNames = overview!!.getList("members", String::class.java)
        playerNames.forEach {
            players.add(StaffPlayers.getCachedStaffPlayer(UUID.fromString(it)))
        }
        return players.toList()
    }

    suspend fun validateOverview(){
        overview = staffColl.retrieveOne("type", "overview")
    }

    suspend fun addMember(player: SickPlayer){
        StaffPlayers.createPlayer(player.uniqueID)
    }

}