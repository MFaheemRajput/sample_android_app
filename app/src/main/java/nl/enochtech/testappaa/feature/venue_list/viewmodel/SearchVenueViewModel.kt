package nl.enochtech.testappaa.feature.venue_list.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.enochtech.testappaa.BuildConfig
import nl.enochtech.testappaa.core.api.ApiClient
import nl.enochtech.testappaa.core.api.ApiStatus
import nl.enochtech.testappaa.core.api.StatusEnum
import nl.enochtech.testappaa.core.data.ApiResponse
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchVenueViewModel(application: Application): AndroidViewModel(application) {
    private var apiStatusObject: ApiStatus? = null
    private val apiStatus = MutableLiveData<ApiStatus>()

    fun getApiStatus(): MutableLiveData<ApiStatus> { return apiStatus }

    fun getVenues(near:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = ApiClient.getRetrofitClient()!!
                val result = client.getSearchedVenues(20210520,10, near, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).enqueue(object :
                    Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.code() == 200) {
                            apiStatusObject = if (response.isSuccessful && response.body() != null){
                                Log.d("B",response.body().toString())
                                val responseModel = Gson().toJson(response.body())
                                ApiStatus(StatusEnum.SUCCESS,responseModel!!)
                            }else{
                                ApiStatus(StatusEnum.ERROR)
                            }
                            apiStatus.postValue(apiStatusObject!!)

                        }
                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        apiStatusObject = ApiStatus(StatusEnum.ERROR)
                        apiStatus.postValue(apiStatusObject!!)
                    }
                })


            }catch ( e :Exception){
                Log.d("faheem",e.message.toString())
                apiStatusObject = ApiStatus(StatusEnum.ERROR)
                apiStatus.postValue(apiStatusObject!!)
            }
        }
    }
}