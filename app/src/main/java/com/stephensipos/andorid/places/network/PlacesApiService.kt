package com.stephensipos.andorid.places.network

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://maps.googleapis.com"
private const val API_KEY = "AIzaSyBl8lEuXT9yeXF5k1mDR_x2WYtAFsjGXKI"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface PlacesApiService {
    @GET("maps/api/place/textsearch/json?key=${API_KEY}")
    fun textSearch(@Query("query") query: String): Call<TextSearchModel>

    @GET("maps/api/place/details/json?key=${API_KEY}&fields=photos")
    fun details(@Query("place_id") place_id: String): Call<DetailsModel>
}

data class TextSearchResult(
    @Json(name="place_id")
    val placeId: String
)

data class DetailsResult(
    @Json(name="photos")
    val photos: List<Photo>
)

data class Photo(
    @Json(name="photo_reference")
    val photoReference: String
)

data class TextSearchModel(
    val results: List<TextSearchResult>,
    val status: String
)

data class DetailsModel(
    val result: DetailsResult,
    val status: String
)
object PlacesApi {
    val retrofitService : PlacesApiService by lazy {
        retrofit.create(PlacesApiService::class.java) }
}

fun getImageUrl(imageRef: String): String {
    return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&key=${API_KEY}&photoreference=${imageRef}"
}
