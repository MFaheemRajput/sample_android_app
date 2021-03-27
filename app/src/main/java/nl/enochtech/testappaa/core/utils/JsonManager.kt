package nl.enochtech.testappaa.core.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import nl.enochtech.testappaa.feature.venue_list.data.Venue

class JsonManager {
    private val gson = Gson()
    companion object {
        private var ourInstance: JsonManager? =
            JsonManager()

        fun getInstance(): JsonManager {
            if (ourInstance == null)
                ourInstance =
                    JsonManager()
            return ourInstance as JsonManager
        }
    }


    fun fromJsonList(response: String): List<Venue> {
        val type = object : TypeToken<List<Venue>>() {}.type
        var response = JsonParser.parseString(response).asJsonObject.get("response")
        val venues = response.asJsonObject.get("venues").asJsonArray
        return gson.fromJson(venues, type)
    }

}