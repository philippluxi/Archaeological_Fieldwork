package com.archaeologicalfieldwork.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.helpers.readImage
import com.archaeologicalfieldwork.helpers.readImageFromPath
import com.archaeologicalfieldwork.helpers.showImagePicker
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel

class SpotActivity : AppCompatActivity(), AnkoLogger {

    var spot = SpotModel()
    lateinit var app: MainApp
    var edit = false

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Add Spot Activity started...")

        app = application as MainApp

        // Retrieve passed Spot info via Parcelize
        if (intent.hasExtra("spot_edit")) {
            edit = true

            spot = intent.extras?.getParcelable<SpotModel>("spot_edit")!!
            spotTitle.setText(spot.title)
            spotDescription.setText(spot.description)
            spotImage.setImageBitmap(readImageFromPath(this, spot.image))
            if (spot.image != null) {
                btnChooseImage.setText(R.string.change_spot_image)
            }

            btnAddSpot.setText(R.string.button_save_spot)
        }

        // Handle Add Button Press
        btnAddSpot.setOnClickListener() {
            spot.title = spotTitle.text.toString()
            spot.description = spotDescription.text.toString()

            if (spot.title.isEmpty()) {
                toast(R.string.enter_spot_title)
            } else {
                if (edit) {
                    app.spots.update(spot.copy())
                } else {
                    app.spots.create(spot.copy())
                }
                info("add Button pressed: ${spot}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        // Handle Add Image Button Press
        btnChooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        // Handle Set Location Button Press
        btnSetLocation.setOnClickListener {
            val location = Location(48.983307948993094, 12.105706251194382, 16f)
            if (spot.zoom != 0F) {
                location.lat = spot.lat
                location.lng = spot.lng
                location.zoom = spot.zoom
            }
            startActivityForResult(
                intentFor<MapActivity>().putExtra("location", location),
                LOCATION_REQUEST
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_spot, menu)
        if (edit && menu != null) {
            menu.getItem(0).isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.spots.delete(spot)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    spot.image = data.getData().toString()
                    spotImage.setImageBitmap((readImage(this, resultCode, data)))
                    btnChooseImage.setText(R.string.change_spot_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    spot.lat = location.lat
                    spot.lng = location.lng
                    spot.zoom = location.zoom
                }
            }
        }
    }
}