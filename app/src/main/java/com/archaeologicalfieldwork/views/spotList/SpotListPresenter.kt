package com.archaeologicalfieldwork.views.spotList

import com.archaeologicalfieldwork.views.*
import com.archaeologicalfieldwork.models.SpotModel


class SpotListPresenter(view: BaseView) : BasePresenter(view) {

    fun doaddSpot() {
        view?.navigateTo(VIEW.SPOT)
    }

    fun doeditSpot(spot: SpotModel) {
        view?.navigateTo(VIEW.SPOT, 0, "spot_edit", spot)
    }

    fun doShowSpotsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadSpots() {
        view?.showSpots(app.spots.findAll())
    }
}