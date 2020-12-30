package com.archaeologicalfieldwork.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.main.MainApp


class SpotListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)
        app = application as MainApp
    }
}