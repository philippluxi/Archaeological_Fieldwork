package com.archaeologicalfieldwork.main

import android.app.Application
import com.archaeologicalfieldwork.models.SpotMemStore
import com.archaeologicalfieldwork.models.SpotModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val spots = SpotMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Archaelogical Fieldwork started")
    }
}