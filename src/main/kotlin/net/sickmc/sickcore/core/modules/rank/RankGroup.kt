package net.sickmc.sickcore.core.modules.rank

import net.sickmc.sickcore.utils.mongo.rankGroupColl
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.redis.publish
import org.bson.Document
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RankGroup(val name: String, val document: Document){

    val prefix = document.getString("publicPrefix")
    val privatePrefix = document.getString("privatePrefix")
    val permissions = document.getList("permissions", String::class.java)
    val priority = document.getInteger("priority")
    val color = document.getInteger("color")
    val discordRoleID = document.getString("discordRoleID")

    fun getChilds(): ArrayList<Rank>{
        val rankNames = document.getList("ranks", String::class.java)
        val ranksCache = arrayListOf<Rank>()
        rankNames.forEach {
            ranksCache.add(Ranks.getCachedRank(it))
        }
        return ranksCache
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
        var groups = HashMap<String, RankGroup>()

        suspend fun load(){
            groups = HashMap()
            rankGroupColl.find().toFlow().collect{
                groups[it.getString("rankgroup")] = RankGroup(it.getString("rankgroup"), it)
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