package net.sickmc.sickcore.core.modules.rank

import kotlinx.coroutines.launch
import net.sickmc.sickcore.utils.mongo.databaseScope
import net.sickmc.sickcore.utils.mongo.rankGroupColl
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.redis.publish
import org.bson.Document
import org.litote.kmongo.coroutine.toList
import java.util.*

class RankGroup(val name: String, val document: Document){

    val prefix = document.getString("publicPrefix")
    val privatePrefix = document.getString("privatePrefix")
    val permissions = document.getList("permissions", String::class.java)
    val priority = document.getInteger("priority")
    val color = document.getInteger("color")
    val coloredPrefix = document.getString("coloredPrefix")
    val discordRoleID = document.getString("discordRoleID")
    val ranks = getChilds()

    fun getChilds(): List<Rank>{
        val rankNames = document.getList("ranks", String::class.java)
        val ranksCache = arrayListOf<Rank>()
        rankNames.forEach {
            ranksCache.add(Ranks.getCachedRank(it))
        }
        return ranksCache.toList()
    }

    suspend fun addPermission(permission: String){
        val permissions = document.getList("permissions", String::class.java)
        if (!permissions.contains(permission))permissions.add(permission)
        document.replace("permissions", permissions)
        rankGroupColl.replace("rankgroup", name, document)
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun removePermission(permission: String){
        val permissions = document.getList("permissions", String::class.java)
        if (permissions.contains(permission)) permissions.remove(permission)
        document.replace("permissions", permissions)
        rankGroupColl.replace("rankgroup", name, document)
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun addRank(rank: Rank){
        val rankNames = document.getList("ranks", String::class.java)
        rankNames.add(rank.name)
        document.replace("ranks", rankNames)
        rankGroupColl.replace("rankgroup", name, document)
        publish("rankupdate", UUID.randomUUID().toString())
    }

}

class RankGroups {

    companion object{
        val groups = HashMap<String, RankGroup>()
        init {
            databaseScope.launch {
                rankGroupColl.collection.find().toList().forEach{
                    groups[it.getString("rankgroup")] = RankGroup(it.getString("rankgroup"), it)
                }
            }
        }
        fun getCachedGroup(name: String): RankGroup{
            return groups[name] ?: error("RankGroup $name cannot be found")
        }
        suspend fun getGroup(name: String): RankGroup?{
            if (groups.contains(name))return groups[name]
            else {
                val document = rankGroupColl.retrieveOne("rankgroup", name) ?: return null
                val group = RankGroup(name, document)
                groups[name] = group
                return group
            }
        }

        suspend fun reloadGroup(name: String): RankGroup?{
            if (groups.contains(name))groups.remove(name)
            val document = rankGroupColl.retrieveOne("rankgroup", name) ?: return null
            val group = RankGroup(name, document)
            groups[name] = group
            return group
        }
    }

}