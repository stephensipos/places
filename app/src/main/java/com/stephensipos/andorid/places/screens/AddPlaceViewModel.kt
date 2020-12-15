package com.stephensipos.andorid.places.screens

import androidx.lifecycle.*
import com.stephensipos.andorid.places.R
import com.stephensipos.andorid.places.database.Place
import com.stephensipos.andorid.places.database.PlaceDao
import com.stephensipos.andorid.places.network.PlacesApi
import com.stephensipos.andorid.places.network.TextSearchModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlaceViewModel(private val placeDao: PlaceDao) : ViewModel() {
    val query = MutableLiveData<String>("")
    val saveError = MutableLiveData<String>("")
    val querySnakeCase = Transformations.map(query) {
        it.toLowerCase().replace(" ", "_").replace(Regex("_+"), "_")
    }

    private val _clickCounter = MutableLiveData<Int>(0)
    val clickCounter: LiveData<Int>
        get() = _clickCounter

    fun increaseCounter() {
        _clickCounter.value = (clickCounter.value)?.inc()
    }

    fun save(): Int? {
        val new_place_query: String = query.value.toString().trim()

        if (new_place_query == "") {
            return R.string.error_empty_input
        }

        savePlace(new_place_query, placeDao)

        return null
    }

    private fun savePlace(query: String, placeDao: PlaceDao) {
        PlacesApi.retrofitService.textSearch(query).enqueue(
            object : Callback<TextSearchModel> {
                override fun onResponse(
                    call: Call<TextSearchModel>,
                    response: Response<TextSearchModel>
                ) {
                    val placeId: String? = response.body()?.results?.get(0)?.placeId

                    if (placeId != null) {
                        viewModelScope.launch {
                            val place: Place = Place(placeId, query)
                            placeDao.insert(place)
                        }
                    } else {
                        saveError.value = "place_id is null"
                    }
                }

                override fun onFailure(call: Call<TextSearchModel>, t: Throwable) {
                    saveError.value = t.message
                }

            }
        )
    }
}