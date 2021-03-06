package com.archaeologicalfieldwork.views.map

import android.os.Bundle
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BaseView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_spot_maps.*

class SpotMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: SpotMapPresenter
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_maps)
        super.init(toolbar, true)

        presenter = initPresenter(SpotMapPresenter(this)) as SpotMapPresenter

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadSpots()
        }
    }

    override fun showSpot(spot: SpotModel) {
        currentTitle.text = spot.title
        currentDescription.text = spot.description
        Glide.with(this).load(spot.image).into(currentImage)
    }

    override fun showSpots(spots: List<SpotModel>) {
        presenter.doPopulateMap(map, spots)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
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