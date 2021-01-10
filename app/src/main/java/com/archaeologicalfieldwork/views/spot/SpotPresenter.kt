package com.archaeologicalfieldwork.views.spot

import android.annotation.SuppressLint
import android.content.Intent
import com.archaeologicalfieldwork.helpers.checkLocationPermissions
import com.archaeologicalfieldwork.helpers.createDefaultLocationRequest
import com.archaeologicalfieldwork.helpers.isPermissionGranted
import com.archaeologicalfieldwork.helpers.showImagePicker
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
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SpotPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {
    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()
    var map: GoogleMap? = null

    var spot = SpotModel()
    var defaultLocation = Location(50.983307948993094, 12.105706251194382, 16f)
    var edit = false
    var locationManualyChanged = false

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
        locationUpdate(spot.location)
    }

    fun doAddOrSave(title: String, description: String) {
        spot.title = title
        spot.description = description
        doAsync {
            if (edit) {
                app.spots.update(spot)
            } else {
                app.spots.create(spot)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun cacheSpot(title: String, description: String) {
        spot.title = title
        spot.description = description
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.spots.delete(spot)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doShare() {   //parent: Activity) {
        val sharingIntent = Intent()
        sharingIntent.type = "text/plain"
        sharingIntent.action = Intent.ACTION_SEND

        val shareBody = "Hey!\nI found this awesome spot here, its name is: ${spot.title}." +
                "\nThis is my description:\n${spot.description}" +
                "\n\nYou can find it here:\nlat: ${spot.location.lat},\nlng: ${spot.location.lng}"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "I found this cool Spot")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

        val sharer = (Intent.createChooser(sharingIntent, "Share via..."))
        view!!.startActivity(sharer)
    }

    fun doSetVisited(isChecked: Boolean) {
        if (isChecked) {
            spot.visited = true
            spot.dateVisited =
                java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
        } else {
            spot.visited = false
            spot.dateVisited = "Not Visited"
        }
    }

    fun doSetRating(rating: Float) {
        spot.rating = rating
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        locationManualyChanged = true
        view?.navigateTo(
            VIEW.LOCATION,
            LOCATION_REQUEST,
            "location",
            Location(spot.location.lat, spot.location.lng, spot.location.zoom)
        )
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    fun locationUpdate(location: Location) {
        spot.location = location
        map?.clear()
        map?.uiSettings?.isZoomGesturesEnabled = true

        val options =
            MarkerOptions().title(spot.title).position(LatLng(spot.location.lat, spot.location.lng))
        map?.addMarker(options)
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    spot.location.lat,
                    spot.location.lng
                ), spot.location.zoom
            )
        )

        view?.showSpot(spot)
    }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    if (!locationManualyChanged) {
                        locationUpdate(Location(l.latitude, l.longitude))
                    }
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
            doSetCurrentLocation()
        } else {
            locationUpdate(Location(defaultLocation.lat, defaultLocation.lng))
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
                spot.location = location
                locationUpdate(location)
            }
        }
    }
}