package nl.enochtech.testappaa.feature.venue_list.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.enochtech.testappaa.feature.venue_list.data.Venue

@Dao
interface VenueDao {

    @Query("SELECT * FROM venue")
    fun getVenuesfromDB():LiveData<List<Venue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVenuesInDb(venues:List<Venue>)

}