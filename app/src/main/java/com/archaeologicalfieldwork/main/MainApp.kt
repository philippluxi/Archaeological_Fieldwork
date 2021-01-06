package com.archaeologicalfieldwork.main

import android.app.Application
import com.archaeologicalfieldwork.models.SpotStore
import com.archaeologicalfieldwork.models.firebase.SpotFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var spots: SpotStore

    override fun onCreate() {
        super.onCreate()
        // spots = SpotJSONStore(applicationContext)
        // spots = SpotStoreRoom(applicationContext)
        spots = SpotFireStore(applicationContext)
        info("Archaeological Fieldwork started")
    }
}