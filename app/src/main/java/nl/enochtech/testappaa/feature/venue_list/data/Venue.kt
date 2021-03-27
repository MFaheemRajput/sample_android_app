package nl.enochtech.testappaa.feature.venue_list.data


data class Venue(val id:String, val name:String, val location: Location?)

data class Contact(val phone:String, val formattedPhone:String)

data class Location(val lat:Double, val lng:Double, val formattedAddress:List<String>)

