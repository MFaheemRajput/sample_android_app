package nl.enochtech.testappaa.feature.venue_list.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import nl.enochtech.testappaa.R
import nl.enochtech.testappaa.core.api.StatusEnum
import nl.enochtech.testappaa.core.base.BaseActivity
import nl.enochtech.testappaa.core.utils.*
import nl.enochtech.testappaa.databinding.ActivityVenueSearchBinding
import nl.enochtech.testappaa.feature.venue_list.adapter.VenueAdapter
import nl.enochtech.testappaa.feature.venue_list.data.Location
import nl.enochtech.testappaa.feature.venue_list.data.Venue
import nl.enochtech.testappaa.feature.venue_list.viewmodel.SearchVenueViewModel

class VenueSearchActivity : BaseActivity() , VenueAdapter.VenueClickListener {

    private lateinit var binding:ActivityVenueSearchBinding
    private var venueList = ArrayList<Venue>()
    private lateinit var venueAdapter: VenueAdapter
    private lateinit var viewModel: SearchVenueViewModel

    fun dummyData(){
        for ( i in 0..9){
            this.venueList.add(Venue("$i", "New park $i", Location(0.0,0.0, arrayListOf("a","b","c")) ))
        }
    }

    fun searchVenueApiCall(){
        if (isNetworkConnected()){
            viewModel.getVenues(binding.searchEdittext.text.toString())
        } else {
            this.showShortToast("No Internet Connection")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // this.dummyData()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_venue_search)
        this.viewModel = ViewModelProvider(this)[SearchVenueViewModel::class.java]

        binding.clearSearch.setOnClickListener {
            binding.searchEdittext.text.clear()
            binding.clearSearch.makeGone()
        }

        binding.recyclerView.layoutManager =  LinearLayoutManager(this)

        venueAdapter = VenueAdapter(this.venueList,this)
        binding.recyclerView.adapter = venueAdapter
        venueAdapter.notifyDataSetChanged()
        binding.noDataAvailableText.makeGone()

        viewModel.getApiStatus().observe(this, Observer { apiStatus ->
            kotlin.run {

                when(apiStatus.statusEnum){

                    StatusEnum.LOADING -> {


                    }

                    StatusEnum.SUCCESS -> {

                         this.venueList.addAll(ArrayList(JsonManager.getInstance().fromJsonList(apiStatus.data.toString())))
                         this.venueAdapter.notifyDataSetChanged()
                         binding.progress.makeGone()
                         binding.noDataAvailableText.makeGone()
                    }

                    StatusEnum.ERROR -> {

                        binding.progress.makeGone()
                        this@VenueSearchActivity.showShortToast("Ops something went wrong")
                        binding.noDataAvailableText.makeGone()
                    }

                    StatusEnum.NODATA -> {

                        binding.progress.makeGone()
                        this@VenueSearchActivity.showShortToast("Ops no venues found")
                        binding.noDataAvailableText.makeGone()
                    }

                }

            }
        })


        binding.searchEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    binding.clearSearch.makeVisible()
                } else {
                    binding.clearSearch.makeGone()
                }
            }
        })

        binding.searchEdittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    binding.progress.makeVisible()
                    binding.noDataAvailableText.makeGone()
                    binding.searchEdittext.hideKeyboard()
                    if (binding.searchEdittext.text.trim().isNotEmpty()) {
                        searchVenueApiCall()
                    } else {
                        this@VenueSearchActivity.showShortToast("Search can't be empty")
                    }
                    return true
                }
                return false
            }
        })

    }



    override fun onCardClicked(position: Int) {



    }
}