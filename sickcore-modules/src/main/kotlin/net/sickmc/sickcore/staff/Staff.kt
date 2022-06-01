package net.sickmc.sickcore.staff

import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.utils.mongo.databaseScope
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

    val overview = loadOverview()
    val players = getStaffPlayers()

    fun getStaffPlayers(): List<StaffPlayer>{
        val players = ArrayList<StaffPlayer>()
        val playerNames = overview.getList("members", String::class.java)
        playerNames.forEach {
            players.add(StaffPlayers.getCachedStaffPlayer(UUID.fromString(it)))
        }
        return players.toList()
    }

    fun loadOverview(): Document{
        var document: Document? = null
        databaseScope.launch {
            document = staffColl.retrieveOne("type", "overview")
        }
        return document ?: error("Staff Overview cannot be loaded")
    }

    suspend fun addMember(player: SickPlayer){
        StaffPlayers.createPlayer(player.uniqueID)
    }

}