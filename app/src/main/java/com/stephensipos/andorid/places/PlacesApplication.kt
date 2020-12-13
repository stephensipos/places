package com.stephensipos.andorid.places

import android.app.Application
import com.stephensipos.andorid.places.database.PlaceDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PlacesApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val database by lazy { PlaceDatabase.getInstance(this, applicationScope) }
}
