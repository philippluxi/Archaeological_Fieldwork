package com.archaeologicalfieldwork.views.spot

import android.content.Intent
import org.jetbrains.anko.intentFor
import com.archaeologicalfieldwork.helpers.showImagePicker
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SpotPresenter(view: BaseView) : BasePresenter(view) {
    var spot = SpotModel()
    var defaultLocation = Location(48.983307948993094, 12.105706251194382, 16f)
    var edit = false
    var map: GoogleMap? = null

    init {
        if (view.intent.hasExtra("spot_edit")) {
            edit = true
            spot = view.intent.extras?.getParcelable<SpotModel>("spot_edit")!!
            view.showSpot(spot)
        } else {
            spot.lat = defaultLocation.lat
            spot.lng = defaultLocation.lng
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(spot.lat, spot.lng)
    }

    fun doAddOrSave(title: String, description: String) {
        spot.title = title
        spot.description = description
        if (edit) {
            app.spots.update(spot)
        } else {
            app.spots.create(spot)
        }
        view?.finish()
    }

    fun cacheSpot (title: String, description: String) {
        spot.title = title
        spot.description = description
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.spots.delete(spot)
        view?.finish()
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(spot.lat, spot.lng, spot.zoom)
            )
        }
    }

    fun locationUpdate(lat: Double, lng: Double) {
        spot.lat = lat
        spot.lng = lng
        spot.zoom = 15f
        map?.clear()
        map?.uiSettings?.isZoomGesturesEnabled = true

        val options = MarkerOptions().title(spot.title).position(LatLng(spot.lat, spot.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(spot.lat, spot.lng), spot.zoom))

        view?.showSpot(spot)
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                spot.image = data.data.toString()
                view?.showSpot(spot)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                spot.lat = location.lat
                spot.lng = location.lng
                spot.zoom = location.zoom
                locationUpdate(spot.lat, spot.lng)
            }
        }
    }
}