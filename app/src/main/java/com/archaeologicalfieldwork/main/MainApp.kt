package com.archaeologicalfieldwork.main

import android.app.Application
import com.archaeologicalfieldwork.models.SpotModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val spots = ArrayList<SpotModel>()

    override fun onCreate() {
        super.onCreate()
        info("Archaelogical Fieldwork started")
    }
}