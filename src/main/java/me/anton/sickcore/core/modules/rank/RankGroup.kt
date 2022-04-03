package me.anton.sickcore.core.modules.rank

import me.anton.sickcore.utils.mongo.MongoDocument

class RankGroup(name: String, val document: MongoDocument){

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

}