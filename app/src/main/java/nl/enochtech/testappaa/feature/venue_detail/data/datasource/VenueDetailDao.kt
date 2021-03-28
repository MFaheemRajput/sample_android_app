package nl.enochtech.testappaa.feature.venue_detail.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail

@Dao
interface VenueDetailDao {

    @Query("SELECT * FROM venuedetail WHERE id = :id")
    fun getVenueDetailfromDB(id:String): LiveData<VenueDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVenueDetailInDb(venueDetial:VenueDetail)

}