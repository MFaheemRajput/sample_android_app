package nl.enochtech.testappaa.feature.venue_detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail
import nl.enochtech.testappaa.feature.venue_list.data.Venue
import nl.enochtech.testappaa.feature.venue_list.repository.VenueDetailRepository
import nl.enochtech.testappaa.feature.venue_list.repository.VenueListRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VenueDetailViewModel (application: Application): AndroidViewModel(application) {
    private var apiStatusObject: ApiStatus? = null
    private val apiStatus = MutableLiveData<ApiStatus>()
    private val repository: VenueDetailRepository = VenueDetailRepository(application)

    fun getApiStatus(): MutableLiveData<ApiStatus> { return apiStatus }

    fun getVenueDetailFromRepo(id:String): LiveData<VenueDetail>?{
        return repository.getVenueDetail(id)
    }

    fun setVenueDetailInRepo(venueDetail:VenueDetail){
        repository.saveVenueDetail(venueDetail)
    }

    fun getVenueDetail(venueId:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = ApiClient.getRetrofitClient()!!
                val result = client.getVenueDetail(venueId,20210520, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET).enqueue(object :
                    Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                            apiStatusObject = if (response.isSuccessful && response.body() != null){
                                Log.d("B",response.body().toString())
                                val responseModel = Gson().toJson(response.body())
                                ApiStatus(StatusEnum.SUCCESS,responseModel!!)
                            } else {
                                ApiStatus(StatusEnum.ERROR)
                            }
                            apiStatus.postValue(apiStatusObject!!)

                    }
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        apiStatusObject = ApiStatus(StatusEnum.ERROR)
                        apiStatus.postValue(apiStatusObject!!)
                    }
                })
            } catch (e :Exception) {
                Log.d("faheem",e.message.toString())
                apiStatusObject = ApiStatus(StatusEnum.ERROR)
                apiStatus.postValue(apiStatusObject!!)
            }
        }
    }


}