package net.sickmc.sickcore.core.games.survival

import net.minecraft.world.entity.Entity
import net.sickmc.sickcore.core.commonPlayer.IGamePlayer
import net.sickmc.sickcore.core.commonPlayer.SickPlayer
import net.sickmc.sickcore.core.commonPlayer.SickPlayers
import net.sickmc.sickcore.utils.Cache
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.mongo.survivalColl
import net.sickmc.sickcore.utils.fabric.getHead
import net.sickmc.sickcore.utils.fabric.textures
import org.bson.Document
import java.util.*
import kotlin.collections.HashMap

class SurvivalPlayer(override val sickPlayer: SickPlayer, override val gameDocument: Document) : IGamePlayer {

    suspend fun addHead(entity: Entity){
        val heads = gameDocument.get("heads", Document::class.java)
        val head = entity.getHead()
        if (heads.getBoolean(head.key.name()))return
        heads.replace(head.key.name(), "true")
        gameDocument.replace("heads", heads)
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
        return SurvivalPlayer(SickPlayers.instance.getEntity(entity), doc)
    }

    override suspend fun reloadEntity(entity: UUID): SurvivalPlayer {
        this[entity] = getEntity(entity)
        return this[entity]!!
    }

    override suspend fun createEntity(entity: UUID): SurvivalPlayer {
        val headList = textures.map { it.key.name() }
        val map = hashMapOf<String, Boolean>()
        headList.forEach {
            map[it] = false
        }
        val playerDoc = Document("uuid", entity.toString())
            .append("deaths", 0)
            .append("heads", map)
        survivalColl.replace("uuid", entity.toString(), playerDoc)
        return SurvivalPlayer(SickPlayers.instance.getEntity(entity), playerDoc)
    }


}