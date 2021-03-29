package nl.enochtech.testappaa.feature.venue_detail.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import nl.enochtech.testappaa.R
import nl.enochtech.testappaa.core.api.StatusEnum
import nl.enochtech.testappaa.core.base.BaseActivity
import nl.enochtech.testappaa.core.utils.JsonManager
import nl.enochtech.testappaa.core.utils.makeGone
import nl.enochtech.testappaa.core.utils.showShortToast
import nl.enochtech.testappaa.databinding.ActivityVenueDetailBinding
import nl.enochtech.testappaa.feature.venue_detail.data.VenueDetail
import nl.enochtech.testappaa.feature.venue_detail.viewmodel.VenueDetailViewModel
import nl.enochtech.testappaa.feature.venue_list.data.Venue

class VenueDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityVenueDetailBinding
    private lateinit var venueDetail:VenueDetail
    private lateinit var viewModel: VenueDetailViewModel

    private fun detailVenueApiCall(){
        if (isNetworkConnected()){
            viewModel.getVenueDetail(venueDetail.id)
        } else {
            this.showShortToast("No Internet Connection")
        }
    }

    private fun parseIntent(){
        if (intent != null){
            val venue:Venue = intent.getParcelableExtra<Venue>("VENUE") as Venue
            venueDetail  = VenueDetail(0, venue.id!!, venue.name!!,venue.location)
        }
    }

    private fun bindData(){
        binding.detailedVenue = this.venueDetail
        binding.executePendingBindings()
        binding.noDataAvailableText.makeGone()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venue_detail)
        this.viewModel = ViewModelProvider(this)[VenueDetailViewModel::class.java]

        parseIntent()
        bindData()
        detailVenueApiCall()

        viewModel.getVenueDetailFromRepo(this.venueDetail.id)!!.observe(this, {
            if(it != null){
                this.venueDetail = it
                bindData()
            }
        })

        viewModel.getApiStatus().observe(this, Observer { apiStatus ->
            kotlin.run {
                when(apiStatus.statusEnum){

                    StatusEnum.LOADING -> {


                    }

                    StatusEnum.SUCCESS -> {
                        venueDetail = JsonManager.getInstance().fromJson(apiStatus.data.toString())
                        this.viewModel.setVenueDetailInRepo(venueDetail)
                        bindData()
                        binding.progress.makeGone()
                        binding.noDataAvailableText.makeGone()

                    }

                    StatusEnum.ERROR -> {

                        binding.progress.makeGone()
                        this@VenueDetailActivity.showShortToast("Ops something went wrong")
                        binding.noDataAvailableText.makeGone()
                    }

                    StatusEnum.NODATA -> {

                        binding.progress.makeGone()
                        this@VenueDetailActivity.showShortToast("Ops no venues found")
                        binding.noDataAvailableText.makeGone()
                    }
                }
            }
        })
    }
}