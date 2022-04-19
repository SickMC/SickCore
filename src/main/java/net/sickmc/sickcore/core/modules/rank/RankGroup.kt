package net.sickmc.sickcore.core.modules.rank

import net.sickmc.sickcore.utils.mongo.MongoDocument
import net.sickmc.sickcore.utils.redis.publish
import java.util.*
import kotlin.collections.ArrayList

class RankGroup(val name: String, val document: MongoDocument){

    val prefix = document.document.getString("publicPrefix")
    val privatePrefix = document.document.getString("privatePrefix")
    val permissions = document.document.getList("permissions", String::class.java)
    val priority = document.document.getInteger("priority")
    val color = document.document.getString("color")
    val coloredPrefix = document.document.getString("coloredPrefix")
    val discordRoleID = document.document.getString("discordRoleID")

    suspend fun getRanks(): List<Rank>{
        val rankNames = document.document.getList("ranks", String::class.java)
        val ranks = ArrayList<Rank>()
        rankNames.forEach {
            ranks.add(Ranks.getRank(it)!!)
        }
        return ranks.toList()
    }

    suspend fun addPermission(permission: String){
        val permissions = document.document.getList("permissions", String::class.java)
        if (!permissions.contains(permission))permissions.add(permission)
        document.document.replace("permissions", permissions)
        document.save()
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun removePermission(permission: String){
        val permissions = document.document.getList("permissions", String::class.java)
        if (permissions.contains(permission)) permissions.remove(permission)
        document.document.replace("permissions", permissions)
        document.save()
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun addRank(rank: Rank){
        val rankNames = document.document.getList("ranks", String::class.java)
        rankNames.add(rank.name)
        document.document.replace("ranks", rankNames)
        document.save()
        publish("rankupdate", UUID.randomUUID().toString())
    }

}