package com.archaeologicalfieldwork.views.spotList

import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread


class SpotListPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

    fun doaddSpot() {
        view?.navigateTo(VIEW.SPOT)
    }

    fun doeditSpot(spot: SpotModel) {
        view?.navigateTo(VIEW.SPOT, 0, "spot_edit", spot)
    }

    fun doHandleFavorite(spot: SpotModel, isFavorite: Boolean) {
        spot.favorite = isFavorite
        app.spots.update(spot)
        info("Spot Updated Favorite: ${(app.spots.findById(spot.id))?.favorite}")
    }

    fun doShowSpotsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doShowSettings() {
        view?.navigateTo(VIEW.SETTINGS)
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.spots.clear()
        view?.navigateTo(VIEW.LOGIN)
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