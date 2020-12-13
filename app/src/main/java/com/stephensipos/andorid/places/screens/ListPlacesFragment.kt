package com.stephensipos.andorid.places.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stephensipos.andorid.places.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stephensipos.andorid.places.PlacesApplication
import com.stephensipos.andorid.places.ViewImagesActivity
import com.stephensipos.andorid.places.database.Place
import com.stephensipos.andorid.places.databinding.FragmentListPlacesBinding
import kotlinx.coroutines.launch

class ListPlacesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = ListPlacesFragmentArgs.fromBundle(requireArguments())
        if (args.placeSaved != null) {
            Toast.makeText(context, "${args.placeSaved} saved!", Toast.LENGTH_LONG).show()
        }

        // Inflate the layout for this fragment
        val binding: FragmentListPlacesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_places, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ListPlacesViewModelFactory(application as PlacesApplication)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(ListPlacesViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = viewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        val adapter = ListPlacesAdapter(
            ClickDeleteListener(
                { place: Place ->
                    lifecycleScope.launch {
                        viewModel.delete(place)
                    }
                }
            ),
            ClickShowImagesListener(
                { place: Place ->
                    // val args = ViewImagesActivityArgs.fromBundle(requireArguments())

                    val intent = Intent (getActivity(), ViewImagesActivity::class.java)
                    intent.putExtra("place_id", place.placeId)
                    getActivity()?.startActivity(intent)
                }
            )
        )

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this.activity)

        viewModel.allPlaces.observe(viewLifecycleOwner) { places ->
            places.let { adapter.submitList(it) }
        }

        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_listPlacesFragment_to_addPlaceFragment)
        }

        return binding.root
    }
}