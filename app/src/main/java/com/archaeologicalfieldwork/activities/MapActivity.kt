package com.archaeologicalfieldwork.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.archaeologicalfieldwork.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val obig = LatLng(48.983307948993094, 12.105706251194382)
        mMap.addMarker(MarkerOptions().position(obig).title("Marker in Oberisling"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(obig, 16f))
    }
}