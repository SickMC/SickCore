package net.sickmc.sickcore.core.commonPlayer

import net.sickmc.sickcore.core.modules.rank.Ranks
import net.sickmc.sickcore.core.modules.staff.StaffModule
import net.sickmc.sickcore.core.modules.rank.Privileges
import net.sickmc.sickcore.utils.Cache
import net.sickmc.sickcore.utils.PlayerUtils
import net.sickmc.sickcore.utils.mongo.players
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.paper.mm
import org.bson.Document
import org.checkerframework.checker.units.qual.K
import java.util.UUID

class SickPlayer(override val uniqueID: UUID, override val document: Document) : ISickPlayer {

    val language = document.getString("language")
    val discordID = document.getString("discordID")
    val name = document.getString("name")
    val extraPermissions = document.getList("extraPermissions", String::class.java)
    val addiction = document.getInteger("addiction")
    val exp = document.getInteger("exp")
    val playtime = document.getLong("playtime")
    val rankExpire = document.getString("rankExpire")
    val privileges: List<Privileges> = document.getList("priveleges", String::class.java).map { s -> Privileges.valueOf(s) }
    val rank = Ranks.getCachedRank(document.getString("rank"))
    val permanentRank = Ranks.getCachedRank(document.getString("permanentRank"))
    val permissions = getPerms()
    val displayName = mm.deserialize("${rank.parent.coloredPrefix}<#5e5e5e> × ${rank.parent.color} $name")

    fun isGreater(name: String): Boolean{
        return Ranks.getCachedRank(name).parent.priority > rank.parent.priority
    }

    private fun getPerms() : List<String>{
        val cache = arrayListOf<String>()
        cache.addAll(extraPermissions)
        cache.addAll(rank.getAllPermissions())
        return cache
    }
    fun isStaff(): Boolean{
        return StaffModule.instance.staff.overview.getList("members", String::class.java).contains(uniqueID.toString())
    }

}
class SickPlayers : Cache<UUID, SickPlayer>, HashMap<UUID, SickPlayer>() {

    companion object{
        lateinit var instance: SickPlayers
    }

    init {
        instance = this
    }
    override fun getCachedEntity(entity: UUID): SickPlayer? {
        return this[entity]
    }

    override suspend fun reloadEntity(entity: UUID): SickPlayer {
        this[entity] = SickPlayer(entity, players.retrieveOne("uuid", entity.toString())!!)
        return this[entity]!!
    }

    override suspend fun createEntity(entity: UUID): SickPlayer {
        val playerDoc = Document("uuid", entity.toString())
            .append("language", "english_uk")
            .append("discordID", "0")
            .append("name", PlayerUtils.fetchName(entity))
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
            .append("privileges", ArrayList<String>())
        players.insertOne(playerDoc)
        this[entity] = SickPlayer(entity, playerDoc)
        return this[entity]!!
    }

    override suspend fun getEntity(entity: UUID): SickPlayer {
        if (players.retrieveOne("uuid", entity.toString()) == null) this[entity] = createEntity(entity)
        else this[entity] = SickPlayer(entity, players.retrieveOne("uuid", entity.toString())!!)
        return this[entity]!!
    }

}