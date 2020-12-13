package com.stephensipos.andorid.places.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stephensipos.andorid.places.PlacesApplication

class AddPlaceViewModelFactory(private val application: PlacesApplication) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AddPlaceViewModel(application.database.placeDao) as T
    }
}