package nl.enochtech.testappaa.feature.venue_list.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.enochtech.testappaa.core.database.AppDatabase
import nl.enochtech.testappaa.feature.venue_list.data.Venue
import nl.enochtech.testappaa.feature.venue_list.data.datasource.VenueDao

class VenueListRepository(application: Application) {

    private val venueDao: VenueDao

    private val venues: LiveData<List<Venue>>

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        venueDao = database!!.venueDao()
        venues = venueDao.getVenuesfromDB()
    }

    fun saveVenues(venues: List<Venue>) = runBlocking {
        this.launch(Dispatchers.IO) {
            try {
                venueDao.saveVenuesInDb(venues)
            } catch (e: Exception) {
                // todo send error to VM
            }
        }
    }

    fun getVenues(): LiveData<List<Venue>>? {
        try {
            return venues
        } catch (e: Exception) {
            // todo send error to VM
            return null
        }
    }
}