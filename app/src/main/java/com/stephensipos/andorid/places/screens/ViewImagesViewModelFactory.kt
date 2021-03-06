package com.stephensipos.andorid.places.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stephensipos.andorid.places.database.PlaceDao

class ViewImagesViewModelFactory(private val placeId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ViewImagesViewModel(placeId) as T
    }
}