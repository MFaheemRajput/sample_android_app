package nl.enochtech.testappaa.core.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import nl.enochtech.testappaa.core.data.ApiResponse
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail
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

    //todo Manage in Feature layer
    fun fromJsonList(response: String): List<Venue> {
        val type = object : TypeToken<List<Venue>>() {}.type
        val responseObject = JsonParser.parseString(response).asJsonObject.get("response")
        val venues = responseObject.asJsonObject.get("venues").asJsonArray
        return gson.fromJson(venues, type)
    }

    //todo Manage in Feature layer
    fun fromJson(response: String): VenueDetail {
        val responseObject = JsonParser.parseString(response).asJsonObject.get("response")
        val venue = responseObject.asJsonObject.get("venue").asJsonObject
        return gson.fromJson(venue, VenueDetail::class.java)

    }

}