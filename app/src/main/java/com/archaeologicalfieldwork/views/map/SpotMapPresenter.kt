package com.archaeologicalfieldwork.views.map

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BasePresenter

class SpotMapPresenter(view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, spots: List<SpotModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        spots.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val spot = app.spots.findById(tag)
        if (spot != null) view?.showSpot(spot)
    }

    fun loadSpots() {
        view?.showSpots(app.spots.findAll())
    }
}