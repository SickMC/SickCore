package me.anton.sickcore.core.player

import me.anton.sickcore.core.modules.rank.Rank
import me.anton.sickcore.core.modules.rank.Ranks
import me.anton.sickcore.utils.mongo.MongoDocument
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import java.util.UUID

class SickPlayer(val uuid: UUID, val document: MongoDocument) {

    val language = document.document.getString("language")
    val discordID = document.document.getString("discordID")
    val name = document.document.getString("name")
    val extraPermissions = document.document.getList("extraPermissions", String::class.java)
    val addiction = document.document.getInteger("addiction")
    val exp = document.document.getInteger("exp")

    suspend fun getRank(): Rank{
        return Ranks.getRank(document.document.getString("rank"))!!
    }

    val rankExpire = document.document.getString("rankExpire")

    suspend fun getPermanentRank(): Rank{
        return Ranks.getRank(document.document.getString("permanentRank"))!!
    }

    suspend fun getPermissions(): List<String>{
        val permissions = ArrayList<String>()
        permissions.addAll(getRank().getAllPermissions())
        permissions.addAll(extraPermissions)
        return permissions
    }

    suspend fun getDisplayname(): Component{
        return MiniMessage.miniMessage().deserialize(getRank().getParent().coloredPrefix + "<gradient:#5e5e5e:#5e5e5e>× </gradient> <${getRank().getParent().color}>$name")
    }

}