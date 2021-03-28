package nl.enochtech.testappaa.feature.venue_list.data

import android.os.Parcelable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.*
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "venue")
@Parcelize
data class Venue(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "tableId" ) val tableId: Int,
    @ColumnInfo(name = "id" ) val id: @RawValue String?,
    @ColumnInfo(name = "name" )val name: @RawValue String?,
    @ColumnInfo(name = "location" ) val location: @RawValue  Location?
) : Parcelable

@Parcelize
data class Location(val formattedAddress: List<String>)  : Parcelable

@BindingAdapter("setFormattedaddress")
fun setFormattedAddress(view: TextView, location: Location?){
    try {
        val formattedAddress = location?.formattedAddress
        var completeAddress = ""
        if (formattedAddress != null) {
            for (element in formattedAddress){
                completeAddress = "$completeAddress $element"
            }
        }else{
            completeAddress = "No address found "
        }
        completeAddress = completeAddress.trim()
        view.text = completeAddress
    } catch (e:Exception){
        view.text = ""
    }
}

class Converters {

    @TypeConverter
    fun fromLocation(location: Location) :String{
        return Gson().toJson(location).toString()
    }

    @TypeConverter
    fun  toLocation(location: String): Location {
        return Gson().fromJson(location, Location::class.java)
    }
}
