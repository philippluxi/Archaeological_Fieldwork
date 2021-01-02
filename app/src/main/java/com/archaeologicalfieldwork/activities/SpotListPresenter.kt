package com.archaeologicalfieldwork.activities


import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.SpotModel

class SpotListPresenter(val view: SpotListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getSpots() = app.spots.findAll()

    fun doaddSpot() {
        view.startActivityForResult<SpotView>(0)
    }

    fun doeditSpot(spot: SpotModel) {
        view.startActivityForResult(view.intentFor<SpotView>().putExtra("spot_edit", spot), 0)
    }

    fun doShowSpotsMap() {
        view.startActivity<SpotMapsActivity>()
    }
}