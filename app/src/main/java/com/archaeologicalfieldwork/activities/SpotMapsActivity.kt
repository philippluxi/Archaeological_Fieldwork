package com.archaeologicalfieldwork.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spot_maps.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.helpers.readImageFromPath

class SpotMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var app: MainApp
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {

        app = application as MainApp

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_maps)
        setSupportActionBar(toolbar)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        app.spots.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val spot = app.spots.findById(tag)
        currentTitle.text = spot!!.title
        currentDescription.text = spot!!.description
        currentImage.setImageBitmap(readImageFromPath(this, spot.image))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}