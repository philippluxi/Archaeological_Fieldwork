package com.archaeologicalfieldwork.views.map

import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SpotMapPresenter(view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, spots: List<SpotModel>) {
        map.uiSettings.isZoomControlsEnabled = true
        spots.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val spot = marker.tag as SpotModel
        doAsync {
            uiThread {
                if (spot != null) view?.showSpot(spot)
            }
        }
    }

    fun loadSpots() {
        doAsync {
            val spots = app.spots.findAll()
            uiThread {
                view?.showSpots(spots)
            }
        }
    }
}