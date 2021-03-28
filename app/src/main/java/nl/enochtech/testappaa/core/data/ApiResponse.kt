package nl.enochtech.testappaa.core.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ApiResponse {
    @SerializedName("response")
    @Expose
    var response: Object?? = null


}


//curl -X GET -G \
//'https://api.foursquare.com/v2/venues/explore' \
//-d client_id="SJAW2NU0GPGXP1UKMA1EQJZNAHIJL5XX2CHQNY3EQEZQ31F3" \
//-d client_secret="TLRRRXOKSGV4H2MFEFR4EMS2CTKDWRPIZAG0GVKWWDWUGCUY" \
//-d v="20180323" \
//-d ll="40.7243,-74.0018" \
//-d query="coffee" \
//-d limit=1