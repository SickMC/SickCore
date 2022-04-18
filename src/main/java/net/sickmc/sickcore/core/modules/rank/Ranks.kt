package net.sickmc.sickcore.core.modules.rank

class Ranks {

    companion object{
        private val ranks = HashMap<String, Rank>()

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