package com.stephensipos.andorid.places.screens

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stephensipos.andorid.places.network.DetailsModel
import com.stephensipos.andorid.places.network.PlacesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ImageView(val source: String)

class ViewImagesViewModel(private val placeId: String) : ViewModel() {
    private var images = ArrayList<ImageView>()
    val imagesLiveData = MutableLiveData<List<ImageView>>(images)

    init {
        PlacesApi.retrofitService.details(placeId).enqueue(
            object : Callback<DetailsModel> {
                override fun onResponse(
                    call: Call<DetailsModel>,
                    response: Response<DetailsModel>
                ) {
                    images = response.body()?.result?.photos?.map {ImageView(it.photoReference)} as ArrayList<ImageView>
                        ?: ArrayList<ImageView>()

                    imagesLiveData.value = images
                }

                override fun onFailure(call: Call<DetailsModel>, t: Throwable) {
                    Log.w("ViewImagesViewModel", t.message.toString())
                }
            }
        )
    }
}