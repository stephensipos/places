package com.stephensipos.andorid.places.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.stephensipos.andorid.places.database.Place
import com.stephensipos.andorid.places.database.PlaceDao

class ListPlacesViewModel (private val placeDao: PlaceDao): ViewModel() {
    var allPlaces = placeDao.getAll().asLiveData()

    suspend fun delete(place: Place) {
        placeDao.delete(place)
    }
}