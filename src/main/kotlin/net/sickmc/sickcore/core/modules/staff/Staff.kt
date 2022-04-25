package net.sickmc.sickcore.core.modules.staff

import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.player.SickPlayer
import net.sickmc.sickcore.utils.mongo.MongoCollection
import net.sickmc.sickcore.utils.mongo.MongoDocument
import net.sickmc.sickcore.utils.mongo.databaseScope
import java.util.UUID

class Staff {

    companion object{
        lateinit var instance: Staff
    }

    init {
        instance = this
    }

    var collection = MongoCollection("staff")
    val overview = loadOverview()
    val players = getStaffPlayers()

    fun getStaffPlayers(): List<StaffPlayer>{
        val players = ArrayList<StaffPlayer>()
        val playerNames = overview.document.getList("members", String::class.java)
        playerNames.forEach {
            players.add(StaffPlayers.getCachedStaffPlayer(UUID.fromString(it)))
        }
        return players.toList()
    }

    fun loadOverview(): MongoDocument{
        var doc: MongoDocument? = null
        databaseScope.launch {
            doc = collection.getDocument("type", "overview")
        }
        return doc ?: error("Staff Overview cannot be loaded")
    }

    suspend fun addMember(player: SickPlayer){
        StaffPlayers.createPlayer(player.uuid)
    }

}