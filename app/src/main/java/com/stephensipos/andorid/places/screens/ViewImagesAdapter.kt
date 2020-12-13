package com.stephensipos.andorid.places.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.databinding.ViewImageLayoutBinding
import com.stephensipos.andorid.places.network.getImageUrl


class ViewImagesAdapter() :
    ListAdapter<ImageView, ViewImagesAdapter.ViewHolder>(ImagesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ViewHolder private constructor(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding: ViewImageLayoutBinding =
            ViewImageLayoutBinding.bind(itemView)

        fun bind(imageView: ImageView?) {
            val url = getImageUrl(imageView?.source.toString())
            Glide.with(itemView).load(url).into(binding.imageView)
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_image_layout, parent, false)
                Log.i("Adapter", "Create ViewHolder")
                return ViewHolder(view)
            }
        }
    }
}

class ImagesComparator : DiffUtil.ItemCallback<ImageView>() {
    override fun areItemsTheSame(oldItem: ImageView, newItem: ImageView): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ImageView, newItem: ImageView): Boolean {
        return oldItem.source == newItem.source
    }
}