package com.stephensipos.andorid.places.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.stephensipos.andorid.places.PlacesApplication
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.databinding.FragmentAddPlaceBinding
import kotlinx.coroutines.launch

class AddPlaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddPlaceBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_place, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddPlaceViewModelFactory(application as PlacesApplication)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel =
            ViewModelProvider(
                this, viewModelFactory).get(AddPlaceViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = viewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        binding.addPlaceButton.setOnClickListener {
            lifecycleScope.launch {
                val result : Int? = viewModel.save()
                if (result != null) {
                    if (container != null) {
                        Snackbar.make(container, result, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    val action = AddPlaceFragmentDirections.actionAddPlaceFragmentToListPlacesFragment()
                    action.placeSaved = viewModel.query.value

                    it.findNavController().navigate(action)
                }
            }

        }

        return binding.root
    }
}