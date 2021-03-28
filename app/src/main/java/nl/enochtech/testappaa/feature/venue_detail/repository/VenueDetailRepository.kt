package nl.enochtech.testappaa.feature.venue_list.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.enochtech.testappaa.core.database.AppDatabase
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail
import nl.enochtech.testappaa.feature.venue_detail.data.datasource.VenueDetailDao

class VenueDetailRepository(application: Application) {

    private val venueDetailDao: VenueDetailDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        venueDetailDao = database!!.venueDetailDao()
    }

    fun saveVenueDetail(venueDetail: VenueDetail) = runBlocking {
        this.launch(Dispatchers.IO) {
            try {
                venueDetailDao.saveVenueDetailInDb(venueDetail)
            } catch (e: Exception) {
                // todo send error to VM
            }
        }
    }


    fun getVenueDetail(id:String): LiveData<VenueDetail>? {
        try {
            return venueDetailDao.getVenueDetailfromDB(id)
        } catch (e: Exception) {
            // todo send error to VM
            return null
        }
    }
}