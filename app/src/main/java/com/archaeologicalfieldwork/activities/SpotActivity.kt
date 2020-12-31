package com.archaeologicalfieldwork.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Add Spot Activity started...")

        app = application as MainApp

        // Retrieve passed Spot info via Parcelize
        if (intent.hasExtra("spot_edit")) {
            info("Detected Intent Extra")
            spot = intent.extras?.getParcelable<SpotModel>("spot_edit")!!
            spotTitle.setText(spot.title)
            spotDescription.setText(spot.desription)
        }

        // Handle Add Button Press
        btnAddSpot.setOnClickListener() {
            spot.title = spotTitle.text.toString()
            spot.desription = spotDescription.text.toString()

            if (spot.title.isNotEmpty()) {
                app.spots.create(spot.copy())
                info("add Button pressed: ${spot}")
                app.spots.logAll()
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            } else {
                toast("Please Enter a title")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_spot, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}