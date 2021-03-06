package net.sickmc.sickcore.commonPlayer

import kotlinx.coroutines.launch
import net.sickmc.sickcore.commonRanks.Privileges
import net.sickmc.sickcore.commonRanks.Ranks
import net.sickmc.sickcore.utils.Cache
import net.sickmc.sickcore.utils.DisplayName
import net.sickmc.sickcore.utils.PlayerUtils
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.mongo.players
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.mongo.staffColl
import org.bson.Document
import java.util.*

class SickPlayer(override val uniqueID: UUID, override val document: Document) : ISickPlayer {

    val language = document.getString("language")
    val discordID = document.getString("discordID")
    val name = document.getString("name")
    val extraPermissions = document.getList("extraPermissions", String::class.java)
    val addiction = document.getInteger("addiction")
    val smucks = document.getInteger("smucks")
    val playtime = document.getLong("playtime")
    val rankExpire = document.getString("rankExpire")
    val privileges: List<Privileges> =
        document.getList("privileges", String::class.java).map { s -> Privileges.valueOf(s) }
    val rank = Ranks.getCachedRank(document.getString("rank"))
    val permanentRank = Ranks.getCachedRank(document.getString("permanentRank"))
    val permissions = getPerms()
    val displayName = DisplayName(
        rank.getParent(),
        name
    )

    fun isGreater(name: String): Boolean {
        return Ranks.getCachedRank(name).getParent().priority > rank.getParent().priority
    }

    fun isAdmin(): Boolean {
        return rank.getParent().name == "Administrator"
    }

    fun isStaff(): Boolean {
        return SickPlayers.staffOverview.getList("member", String::class.java).contains(name)
    }

    private fun getPerms(): List<String> {
        val cache = arrayListOf<String>()
        cache.addAll(extraPermissions)
        cache.addAll(rank.getPermissions())
        return cache
    }

}

class SickPlayers : Cache<UUID, SickPlayer>, HashMap<UUID, SickPlayer>() {

    companion object {
        lateinit var instance: SickPlayers

        lateinit var staffOverview: Document
    }

    init {
        instance = this
        databaseScope.launch {
            staffOverview = staffColl.retrieveOne("type", "overview")!!
        }
    }

    override fun getCachedEntity(entity: UUID): SickPlayer? {
        return this[entity]
    }

    override suspend fun reloadEntity(entity: UUID): SickPlayer? {
        this[entity] = getEntity(entity) ?: return null
        return this[entity]
    }

    override suspend fun createEntity(entity: UUID): SickPlayer {
        val playerDoc = Document("uuid", entity.toString())
            .append("language", "english_uk")
            .append("discordID", "0")
            .append("name", PlayerUtils.fetchName(entity))
            .append("addiction", 0)
            .append("smucks", 0)
            .append("extraPermissions", ArrayList<String>())
            .append("rank", "Player")
            .append("rankExpire", "none")
            .append("permanentRank", "Player")
            .append("mates", arrayListOf<String>())
            .append("bubble", "")
            .append("achievements", arrayListOf<String>())
            .append("firstJoin", System.currentTimeMillis())
            .append("playtime", 0L)
            .append("privileges", ArrayList<String>())
        players.insertOne(playerDoc)
        this[entity] = SickPlayer(entity, playerDoc)
        return this[entity]!!
    }

    override suspend fun getEntity(entity: UUID): SickPlayer? {
        if (players.retrieveOne("uuid", entity.toString()) == null) return null
        else this[entity] = SickPlayer(entity, players.retrieveOne("uuid", entity.toString())!!)
        return this[entity]!!
    }

}