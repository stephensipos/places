package com.stephensipos.andorid.places.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.databinding.ViewImagesFragmentBinding

class ViewImagesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding: ViewImagesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.view_images_fragment, container, false)

        val placeId = activity?.intent?.getStringExtra("place_id").toString()

        if (placeId != null) {
            val viewModelFactory = ViewImagesViewModelFactory(placeId)

            // Get a reference to the ViewModel associated with this fragment.
            val viewModel =
                ViewModelProvider(
                    this, viewModelFactory
                ).get(ViewImagesViewModel::class.java)

            // To use the View Model with data binding, you have to explicitly
            // give the binding object a reference to it.
            binding.viewModel = viewModel

            // Specify the current activity as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe LiveData updates.
            binding.lifecycleOwner = this

            val adapter = ViewImagesAdapter()

            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(this.activity)

            viewModel.imagesLiveData.observe(viewLifecycleOwner) { images ->
                images.let { adapter.submitList(it) }
            }
        }

        return binding.root
    }
}