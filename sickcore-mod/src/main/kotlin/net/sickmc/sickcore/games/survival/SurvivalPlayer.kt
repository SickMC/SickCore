package net.sickmc.sickcore.games.survival

import net.sickmc.sickcore.commonPlayer.IGamePlayer
import net.sickmc.sickcore.commonPlayer.SickPlayer
import net.sickmc.sickcore.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.Cache
import net.sickmc.sickcore.utils.fabric.MobHead
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.mongo.survivalColl
import net.sickmc.sickcore.utils.fabric.textures
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap

class SurvivalPlayer(override val sickPlayer: SickPlayer, override val gameDocument: Document) : IGamePlayer {

    suspend fun addHead(head: MobHead){
        val heads = gameDocument.get("heads", Document::class.java)
        if (heads.getBoolean(head.attributes.name.replace(" ", "_")))return
        heads.replace(head.attributes.name.replace(" ", "_"), true)
        gameDocument.replace("heads", heads)
        survivalColl.replace("uuid", sickPlayer.uniqueID.toString(), gameDocument)
    }

    suspend fun addDeath(){
        gameDocument.replace("deaths", gameDocument.getInteger("deaths").plus(1))
        survivalColl.replace("uuid", sickPlayer.uniqueID.toString(), gameDocument)
    }

}

class SurvivalPlayers : Cache<UUID, SurvivalPlayer>, HashMap<UUID, SurvivalPlayer>() {

    companion object{
        lateinit var instance: SurvivalPlayers
    }

    init {
        instance = this
    }

    override fun getCachedEntity(entity: UUID): SurvivalPlayer? {
        return this[entity]
    }

    override suspend fun getEntity(entity: UUID): SurvivalPlayer {
        val doc = survivalColl.retrieveOne("uuid", entity.toString()) ?: return createEntity(entity)
        return SurvivalPlayer(SickPlayers.instance.getEntity(entity)!!, doc)
    }

    override suspend fun reloadEntity(entity: UUID): SurvivalPlayer {
        this[entity] = getEntity(entity)
        return this[entity]!!
    }

    override suspend fun createEntity(entity: UUID): SurvivalPlayer {
        val headList = textures.map { it.value.name.replace(" ", "_") }.toMutableList()
        headList.addAll(extraHeads.map { it.name.replace(" ", "_") })
        val map = hashMapOf<String, Boolean>()
        headList.forEach {
            map[it] = false
        }
        val playerDoc = Document("uuid", entity.toString())
            .append("deaths", 0)
            .append("heads", map)
        survivalColl.insertOne(playerDoc)
        return SurvivalPlayer(SickPlayers.instance.getEntity(entity)!!, playerDoc)
    }


}