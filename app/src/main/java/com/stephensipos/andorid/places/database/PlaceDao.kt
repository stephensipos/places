package com.stephensipos.andorid.places.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place_table ORDER BY \"query\"")
    fun getAll(): Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: Place)

    @Delete
    suspend fun delete(place: Place)

    @Query("DELETE FROM place_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM place_table WHERE place_id=:place_id ")
    fun getItem(place_id: String): LiveData<Place>
}