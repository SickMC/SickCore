package net.sickmc.sickcore.utils

import net.kyori.adventure.text.Component
import org.bson.json.JsonObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class PlayerUtils {

    companion object{
        fun fetchName(uuid: UUID): String{
            val url = URL("https://api.minetools.eu/uuid/$uuid")
            val connection = url.openConnection() as HttpURLConnection

            val raw = connection.inputStream.bufferedReader().readText()
            connection.disconnect()
            val jsonObject = JsonObject(raw)
            return jsonObject.toBsonDocument().getString("name").value
        }

        fun fetchUUID(name: String): UUID{
            val url = URL("https://api.minetools.eu/uuid/$name")
            val connection = url.openConnection() as HttpURLConnection

            val raw = connection.inputStream.bufferedReader().readText()
            connection.disconnect()
            val jsonObject = JsonObject(raw)
            return jsonObject.toBsonDocument().getString("id").value.transformToUUID()
        }
    }

}

