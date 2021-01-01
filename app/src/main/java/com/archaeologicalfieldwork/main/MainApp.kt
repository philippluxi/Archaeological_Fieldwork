package com.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.models.SpotMemStore
import com.archaeologicalfieldwork.models.SpotStore

class MainApp : Application(), AnkoLogger {

    lateinit var spots: SpotStore

    override fun onCreate() {
        super.onCreate()
        spots = SpotMemStore()
        info("Archaelogical Fieldwork started")
    }
}