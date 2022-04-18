package me.anton.sickcore.core.modules.rank

class RankGroups {

    companion object{
        private val groups = HashMap<String, RankGroup>()

        suspend fun getGroup(name: String): RankGroup?{
            if (groups.contains(name))return groups[name]!!
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