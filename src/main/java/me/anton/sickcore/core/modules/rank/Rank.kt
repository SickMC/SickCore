package me.anton.sickcore.core.modules.rank

import me.anton.sickcore.utils.mongo.MongoDocument
import me.anton.sickcore.utils.redis.publish
import java.util.*
import kotlin.collections.ArrayList

class Rank(val name: String, val document: MongoDocument) {

    val privateDiscordID = document.document.getString("discordID")
    val extraPermissions = document.document.getList("extraPermissions", String::class.java)

    suspend fun getParent(): RankGroup{
        return RankGroups.getGroup(document.document.getString("parent"))!!
    }

    suspend fun getAllPermissions(): List<String>{
        val permissions = ArrayList<String>()
        permissions.addAll(getParent().permissions)
        permissions.addAll(extraPermissions)
        return permissions
    }

    suspend fun reload(){
        Ranks.reloadRank(name)
    }

    suspend fun addExtraPermission(permission: String){
        val permissions = document.document.getList("extraPermissions", String::class.java)
        if (!permissions.contains(permission))permissions.add(permission)
        document.document.replace("extraPermissions", permissions)
        document.save()
        publish("rankupdate", UUID.randomUUID().toString())
    }

    suspend fun removeExtraPermission(permission:String){
        val permissions = document.document.getList("extraPermissions", String::class.java)
        if (permissions.contains(permission))permissions.remove(permission)
        document.document.replace("extraPermissions", permissions)
        document.save()
        publish("rankupdate", UUID.randomUUID().toString())
    }

}