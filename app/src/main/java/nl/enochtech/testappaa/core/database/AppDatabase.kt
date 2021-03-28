package nl.enochtech.testappaa.core.database

import android.content.Context
import androidx.room.*
import nl.enochtech.testappaa.feature.venue_detail.data.VenueConverters
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail
import nl.enochtech.testappaa.feature.venue_detail.data.datasource.VenueDetailDao
import nl.enochtech.testappaa.feature.venue_list.data.Converters
import nl.enochtech.testappaa.feature.venue_list.data.Venue
import nl.enochtech.testappaa.feature.venue_list.data.datasource.VenueDao

@Database(entities = [Venue::class, VenueDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, VenueConverters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun venueDao(): VenueDao
    abstract fun venueDetailDao(): VenueDetailDao
    
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        AppDatabase::class.java,
                        "place_db")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}