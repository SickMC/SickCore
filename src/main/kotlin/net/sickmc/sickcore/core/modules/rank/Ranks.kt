package net.sickmc.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.mongo.MongoDocument
import net.sickmc.sickcore.utils.mongo.databaseScope
import org.litote.kmongo.coroutine.toList

class Ranks {

    companion object{
        val ranks = HashMap<String, Rank>()

        init {
            databaseScope.launch {
                val collection = RankModule.instance.rankCollection.collection
                collection.collection.find().toList().forEach{
                    ranks[it.getString("rank")] = Rank(it.getString("rank"), MongoDocument(collection, "rank", it.getString("rank"), it))
                }
            }
        }
        fun getCachedRank(name: String): Rank{
            return ranks[name] ?: error("Rank $name cannot be found")
        }
        suspend fun getRank(name: String): Rank?{
            if (ranks.contains(name))return ranks[name]!!
            else {
                val document = RankModule.instance.rankCollection.getDocument("rank", name) ?: return null
                val rank = Rank(name, document)
                ranks[name] = rank
                return rank
            }
        }

        suspend fun reloadRank(name: String): Rank?{
            if (ranks.contains(name)) ranks.remove(name)
            val document = RankModule.instance.rankCollection.getDocument("rank", name) ?: return null
            val rank = Rank(name, document)
            ranks[name] = rank
            return rank
        }
    }


}