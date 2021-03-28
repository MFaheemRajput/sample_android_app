package nl.enochtech.testappaa.feature.venue_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import nl.enochtech.testappaa.R
import nl.enochtech.testappaa.databinding.VenueCellBinding
import nl.enochtech.testappaa.feature.venue_list.data.Venue

class VenueAdapter(private val list: ArrayList<Venue>, private var itemClickListener: VenueClickListener): RecyclerView.Adapter<VenueAdapter.ClassVH>() {

    lateinit var binding: VenueCellBinding
    lateinit var inflater: LayoutInflater
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassVH {

        context = parent.context
        inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.venue_cell, parent, false)

        return ClassVH(
            binding,
            this@VenueAdapter
        )
    }

    override fun onBindViewHolder(holder: ClassVH, position: Int) {
        val item: Venue = list[position]

        holder.bind(item, position)
    }

    fun onCardClicked(item: Venue, position: Int) {

        this.itemClickListener.onCardClicked(position)
    }

    interface VenueClickListener {
        fun onCardClicked(position : Int)
    }

    override fun getItemCount(): Int = list.size

    class ClassVH(viewParam: VenueCellBinding, adapterParam: VenueAdapter) :
        RecyclerView.ViewHolder(viewParam.root) {

        private var binding: VenueCellBinding = viewParam
        private var adapter: VenueAdapter = adapterParam

        fun bind(item: Venue, pos: Int) {

            this.binding.venue = item
            this.binding.position = pos
            this.binding.adapter = this.adapter
            this.binding.executePendingBindings()

        }
    }

}

