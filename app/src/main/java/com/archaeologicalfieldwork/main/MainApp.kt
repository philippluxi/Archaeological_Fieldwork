package com.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.models.SpotMemStore

class MainApp : Application(), AnkoLogger {

    val spots = SpotMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Archaelogical Fieldwork started")
    }
}