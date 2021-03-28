package nl.enochtech.testappaa.feature.venue_detail.data

import android.os.Parcelable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import nl.enochtech.testappaa.feature.venue_list.data.Location

@Entity(tableName = "venuedetail")
@Parcelize
data class VenueDetail(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "tableId" ) val tableId: Int,
    @ColumnInfo(name = "id" ) val id:String,
    @ColumnInfo(name = "name" ) val name:String,
    @ColumnInfo(name = "location" ) var location: Location? = null,
    @ColumnInfo(name = "contact" ) var contact: Contact? = null,
    @ColumnInfo(name = "description" ) val description: String? = "",
    @ColumnInfo(name = "shortUrl" ) val shortUrl: String? = "",
    @ColumnInfo(name = "verified" ) val verified: Boolean? = false,

) : Parcelable

@Parcelize
data class Contact(val phone: @RawValue String, val formattedPhone: @RawValue String)  : Parcelable

@BindingAdapter("setFormattedContact")
fun setFormattedContact(view: TextView, contact: Contact?){
    if (contact == null){
        view.text = String("No Contact found  ".toCharArray())
    } else {
        val phone = contact.phone ?: ""
        view.text = String("Phone $phone".toCharArray())
    }
}

@BindingAdapter("setVerifiedValue")
fun setVerifiedValue(view: TextView, value: Boolean){
    if (value){
        view.text = String("Verified".toCharArray())

    } else {
        view.text = String("Not Verified".toCharArray())
    }
}

class VenueConverters {

    @TypeConverter
    fun fromContact(contact: Contact) :String{
        return Gson().toJson(contact).toString()
    }

    @TypeConverter
    fun  toContact(contact: String): Contact {
        return Gson().fromJson(contact, Contact::class.java)
    }
}