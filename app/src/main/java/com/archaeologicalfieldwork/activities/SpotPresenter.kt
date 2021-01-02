package com.archaeologicalfieldwork.activities

import android.content.Intent
import org.jetbrains.anko.intentFor
import com.archaeologicalfieldwork.helpers.showImagePicker
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel

class SpotPresenter(val view: SpotView) {
    var spot = SpotModel()
    var location = Location(48.983307948993094, 12.105706251194382, 16f)
    var app: MainApp
    var edit = false

    // Request Codes
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("spot_edit")) {
            edit = true
            spot = view.intent.extras?.getParcelable<SpotModel>("spot_edit")!!
            view.showSpot(spot)
        }
    }

    fun doAddOrSave(title: String, description: String) {
        spot.title = title
        spot.description = description
        if (edit) {
            app.spots.update(spot)
        } else {
            app.spots.create(spot)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.spots.delete(spot)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation() {
        if (spot.zoom != 0f) {
            location.lat = spot.lat
            location.lng = spot.lng
            location.zoom = spot.zoom
        }
        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
    }
    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                spot.image = data.data.toString()
                view.showSpot(spot)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                spot.lat = location.lat
                spot.lng = location.lng
                spot.zoom = location.zoom
            }
        }
    }
}