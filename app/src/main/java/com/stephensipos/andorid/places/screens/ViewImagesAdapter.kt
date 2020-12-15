package com.stephensipos.andorid.places.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.databinding.ViewImageHeaderLayoutBinding
import com.stephensipos.andorid.places.databinding.ViewImageLayoutBinding
import com.stephensipos.andorid.places.network.getImageUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class ViewImagesAdapter() :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(ImagesComparator()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.create(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.create(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = getItem(position)
        when (holder) {
            is ViewHolder -> {
                holder.bind(current as ImageView)
            }
            is TextViewHolder -> {
                holder.bind(current as Header)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<ImageView>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(Header)
                else -> listOf(Header) + list.map { ImageView(it.source) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).id) {
            "header"-> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class TextViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding: ViewImageHeaderLayoutBinding =
            ViewImageHeaderLayoutBinding.bind(itemView)

        fun bind(header: Header) {
            // Nothing to bind here
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): TextViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_image_header_layout, parent, false)
                return TextViewHolder(view)
            }
        }
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
                return ViewHolder(view)
            }
        }
    }
}

class ImagesComparator : DiffUtil.ItemCallback<DataItem >() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
}