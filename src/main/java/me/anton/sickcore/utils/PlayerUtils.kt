package me.anton.sickcore.utils

import org.bson.json.JsonObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class PlayerUtils {

    companion object{
        inline fun fetchName(uuid: UUID, callback: (String) -> Unit){
            val url = URL("https://api.minetools.eu/uuid/$uuid")
            val connection = url.openConnection() as HttpURLConnection

            val raw = connection.inputStream.bufferedReader().readText()
            val jsonObject = JsonObject(raw)
            callback(jsonObject.toBsonDocument().getString("name").value)
        }

        fun fetchName(uuid: UUID): String{
            val url = URL("https://api.minetools.eu/uuid/$uuid")
            val connection = url.openConnection() as HttpURLConnection

            val raw = connection.inputStream.bufferedReader().readText()
            val jsonObject = JsonObject(raw)
            return jsonObject.toBsonDocument().getString("name").value
        }
    }

}

