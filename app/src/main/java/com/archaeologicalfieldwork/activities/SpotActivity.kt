package com.archaeologicalfieldwork.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.SpotModel

class SpotActivity : AppCompatActivity(), AnkoLogger {

    var spot = SpotModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)
        app = application as MainApp

        btnAddSpot.setOnClickListener() {
            spot.title = spotTitle.text.toString()
            spot.desription = spotDescription.text.toString()

            if (spot.title.isNotEmpty()) {
                app.spots.add(spot.copy())
                info("add Button pressed: ${spot}")
                for (i in app.spots.indices) {
                    info("Spot [$i]: ${app.spots[i]}")
                }
            } else {
                toast("Please Enter a title")
            }
        }
    }
}