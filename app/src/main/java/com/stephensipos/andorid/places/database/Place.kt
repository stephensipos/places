package com.stephensipos.andorid.places.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="place_table")
class Place (
    @PrimaryKey
    @ColumnInfo(name="place_id")
    val placeId: String,

    @NonNull
    @ColumnInfo(name="query")
    val query: String
)