package nl.enochtech.testappaa.core.api

import nl.enochtech.testappaa.core.data.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteApiService {
    @GET("venues/search")
    fun getSearchedVenues(
        @Query("v") v: Int,
        @Query("limit") limit: Int,
        @Query("near") near: String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
    ): Call<ApiResponse>

    @GET("venues/{venue_id}")
    fun getVenueDetail(
            @Path("venue_id") venue_id: String,
            @Query("v") v: Int,
            @Query("client_id") client_id: String,
            @Query("client_secret") client_secret: String,
    ): Call<ApiResponse>

}

//curl -X GET -G \
//'https://api.foursquare.com/v2/venues/explore' \
//-d client_id="SJAW2NU0GPGXP1UKMA1EQJZNAHIJL5XX2CHQNY3EQEZQ31F3" \
//-d client_secret="TLRRRXOKSGV4H2MFEFR4EMS2CTKDWRPIZAG0GVKWWDWUGCUY" \
//-d v="20180323" \
//-d ll="40.7243,-74.0018" \
//-d query="coffee" \
//-d limit=1