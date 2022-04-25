package net.sickmc.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import net.sickmc.sickcore.core.player.SickPlayers
import net.sickmc.sickcore.utils.mongo.MongoDocument
import net.sickmc.sickcore.utils.mongo.databaseScope
import org.litote.kmongo.coroutine.toList

class RankGroups {

    companion object{
        val groups = HashMap<String, RankGroup>()
        init {
            databaseScope.launch {
                val collection = RankModule.instance.groupCollection.collection
                collection.collection.find().toList().forEach{
                    groups[it.getString("rankgroup")] = RankGroup(it.getString("rankgroup"), MongoDocument(collection, "rankgroup", it.getString("rankgroup"), it))
                }
            }
        }
        fun getCachedGroup(name: String): RankGroup{
            return groups[name] ?: error("RankGroup $name cannot be found")
        }
        suspend fun getGroup(name: String): RankGroup?{
            if (groups.contains(name))return groups[name]
            else {
                val document = RankModule.instance.groupCollection.getDocument("rankgroup", name) ?: return null
                val group = RankGroup(name, document)
                groups[name] = group
                return group
            }
        }

        suspend fun reloadGroup(name: String): RankGroup?{
            if (groups.contains(name))groups.remove(name)
            val document = RankModule.instance.groupCollection.getDocument("rankgroup", name) ?: return null
            val group = RankGroup(name, document)
            groups[name] = group
            return group
        }
    }

}