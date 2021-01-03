package com.archaeologicalfieldwork.views.spot

import android.annotation.SuppressLint
import android.content.Intent
import com.archaeologicalfieldwork.helpers.checkLocationPermissions
import com.archaeologicalfieldwork.helpers.createDefaultLocationRequest
import com.archaeologicalfieldwork.helpers.isPermissionGranted
import org.jetbrains.anko.intentFor
import com.archaeologicalfieldwork.helpers.showImagePicker
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SpotPresenter(view: BaseView) : BasePresenter(view) {
    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var map: GoogleMap? = null

    var spot = SpotModel()
    var defaultLocation = Location(50.983307948993094, 12.105706251194382, 16f)
    var edit = false

    init {
        if (view.intent.hasExtra("spot_edit")) {
            edit = true
            spot = view.intent.extras?.getParcelable<SpotModel>("spot_edit")!!
            view.showSpot(spot)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
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

    fun cacheSpot(title: String, description: String) {
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
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(spot.lat, spot.lng, spot.zoom))
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
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

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun doRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (isPermissionGranted(requestCode, grantResults)) {
            if (isPermissionGranted(requestCode, grantResults)) {
                doSetCurrentLocation()
            } else {
                locationUpdate(defaultLocation.lat, defaultLocation.lng)
            }
        }
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