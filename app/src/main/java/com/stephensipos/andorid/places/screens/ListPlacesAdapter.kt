package com.stephensipos.andorid.places.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.database.Place
import com.stephensipos.andorid.places.databinding.ListPlacesItemLayoutBinding


class ClickDeleteListener(val clickListener: (place: Place) -> Unit) {
    fun onClick(place: Place) = clickListener(place)
}

class ClickShowImagesListener(val clickListener: (place: Place) -> Unit) {
    fun onClick(place: Place) = clickListener(place)
}

class ListPlacesAdapter(
    private val deleteHandler: ClickDeleteListener,
    private val showImagesHandler: ClickShowImagesListener
) :
    ListAdapter<Place, ListPlacesAdapter.ViewHolder>(PlacesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent, deleteHandler, showImagesHandler)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ViewHolder private constructor(
        itemView: View,
        private val deleteHandler: ClickDeleteListener,
        private val showImagesHandler: ClickShowImagesListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding: ListPlacesItemLayoutBinding =
            ListPlacesItemLayoutBinding.bind(itemView)

        fun bind(place: Place?) {

            binding.place = place
            binding.clickDeleteListener = deleteHandler
            binding.clickShowImagesListener = showImagesHandler
        }

        companion object {
            fun create(
                parent: ViewGroup,
                deleteHandler: ClickDeleteListener,
                showImagesHandler: ClickShowImagesListener
            ): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_places_item_layout, parent, false)
                return ViewHolder(view, deleteHandler, showImagesHandler)
            }
        }
    }
}

class PlacesComparator : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.placeId == newItem.placeId
    }
}