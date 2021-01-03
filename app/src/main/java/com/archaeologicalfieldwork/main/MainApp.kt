package com.archaeologicalfieldwork.main

import android.app.Application
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.models.SpotStore
import com.archaeologicalfieldwork.models.json.SpotJSONStore

class MainApp : Application(), AnkoLogger {

    lateinit var spots: SpotStore

    override fun onCreate() {
        super.onCreate()
        spots = SpotJSONStore(applicationContext)
        info("Archaelogical Fieldwork started")
    }
}