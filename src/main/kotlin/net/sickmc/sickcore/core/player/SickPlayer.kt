package net.sickmc.sickcore.core.player

import net.sickmc.sickcore.core.modules.rank.Rank
import net.sickmc.sickcore.core.modules.rank.Ranks
import net.sickmc.sickcore.core.modules.staff.StaffModule
import net.sickmc.sickcore.utils.mongo.MongoDocument
import net.kyori.adventure.text.Component
import net.sickmc.sickcore.core.modules.rank.Privileges
import net.sickmc.sickcore.utils.paper.mm
import java.util.UUID

class SickPlayer(val uuid: UUID, val document: MongoDocument) {

    val language = document.document.getString("language")
    val discordID = document.document.getString("discordID")
    val name = document.document.getString("name")
    val extraPermissions = document.document.getList("extraPermissions", String::class.java)
    val addiction = document.document.getInteger("addiction")
    val exp = document.document.getInteger("exp")
    val playtime = document.document.getLong("playtime")
    val rankExpire = document.document.getString("rankExpire")
    val privileges: List<Privileges> = getPrivileges()
    val rank = Ranks.getCachedRank(document.document.getString("rank"))
    val permanentRank = Ranks.getCachedRank(document.document.getString("permanentRank"))
    val permissions = getPerms()
    val displayName = mm.deserialize("${rank.parent.coloredPrefix}<#5e5e5e> × ${rank.parent.color} $name")

     fun getPerms(): List<String>{
        val permissions = ArrayList<String>()
        permissions.addAll(rank.getAllPermissions())
        permissions.addAll(extraPermissions)
        return permissions
     }

    fun isGreater(name: String): Boolean{
        return Ranks.getCachedRank(name).parent.priority > rank.parent.priority
    }

    suspend fun isStaff(): Boolean{
        return StaffModule.instance.staff.overview.document.getList("members", String::class.java).contains(uuid.toString())
    }

    fun getPrivileges(): ArrayList<Privileges>{
        val cache = arrayListOf<Privileges>()
        document.document.getList("privileges", String::class.java).forEach {string ->
            cache.add(Privileges.valueOf(string))
        }
        return cache
    }

}