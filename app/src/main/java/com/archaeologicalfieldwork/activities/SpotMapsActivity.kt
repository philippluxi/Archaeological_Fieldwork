package com.archaeologicalfieldwork.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.archaeologicalfieldwork.R
import kotlinx.android.synthetic.main.activity_spot_list.*

class SpotMapsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_maps)

        setSupportActionBar(toolbar)
        toolbar.title = title
    }
}