package com.stephensipos.andorid.places.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Place::class], version=1, exportSchema=false)
abstract class PlaceDatabase: RoomDatabase() {
    abstract val placeDao: PlaceDao

    companion object {
        @Volatile
        private var INSTANCE: PlaceDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope
        ) : PlaceDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlaceDatabase::class.java,
                        "place_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(PlaceDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

    private class PlaceDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.placeDao)
                }
            }
        }

        suspend fun populateDatabase(placeDao: PlaceDao) {
            // Delete all content here.
            placeDao.deleteAll()

            // Add sample words.
            var place = Place("Budapest", "Budapest")
            placeDao.insert(place)
        }
    }
}