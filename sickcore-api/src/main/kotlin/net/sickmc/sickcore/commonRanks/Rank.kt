package net.sickmc.sickcore.commonRanks

import net.sickmc.sickcore.utils.mongo.rankColl
import net.sickmc.sickcore.utils.mongo.replace
import net.sickmc.sickcore.utils.mongo.retrieveOne
import net.sickmc.sickcore.utils.redis.publish
import org.bson.Document
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Rank(val name: String, val document: Document) {

    val privateDiscordID = document.getString("discordID")
    val extraPermissions = document.getList("extraPermissions", String::class.java)
    fun getPermissions(): ArrayList<String>{
        val permissions = ArrayList<String>()
        permissions.addAll(getParent().permissions)
        permissions.addAll(extraPermissions)
        return permissions
    }

    fun getParent(): RankGroup {
        return RankGroups.getCachedGroup(document.getString("parent"))
    }

    suspend fun reload(){
        Ranks.reloadRank(name)
    }

    suspend fun addExtraPermission(permission: String){
        val permissions = document.getList("extraPermissions", String::class.java)
        if (!permissions.contains(permission))permissions.add(permission)
        document.replace("extraPermissions", permissions)
        rankColl.replace("rank", name, document)
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun removeExtraPermission(permission:String){
        val permissions = document.getList("extraPermissions", String::class.java)
        if (permissions.contains(permission))permissions.remove(permission)
        document.replace("extraPermissions", permissions)
        rankColl.replace("rank", name, document)
        publish("rankupdate", UUID.randomUUID().toString())
    }

}

class Ranks {

    companion object{
        var ranks = HashMap<String, Rank>()

        suspend fun load(){
            ranks = HashMap()
            rankColl.find().toFlow().collect{
                ranks[it.getString("rank")] = Rank(it.getString("rank"), it)
            }
        }
        fun getCachedRank(name: String): Rank {
            return ranks[name] ?: error("Rank $name cannot be found")
        }
        suspend fun getRank(name: String): Rank?{
            if (ranks.contains(name))return ranks[name]!!
            else {
                val document = rankColl.retrieveOne("rank", name) ?: return null
                val rank = Rank(name, document)
                ranks[name] = rank
                return rank
            }
        }

        suspend fun reloadRank(name: String): Rank?{
            if (ranks.contains(name)) ranks.remove(name)
            val document = rankColl.retrieveOne("rank", name) ?: return null
            val rank = Rank(name, document)
            ranks[name] = rank
            return rank
        }
    }


}