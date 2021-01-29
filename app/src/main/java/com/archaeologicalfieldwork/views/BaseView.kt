package com.archaeologicalfieldwork.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.location.EditLocationView
import com.archaeologicalfieldwork.views.login.LoginView
import com.archaeologicalfieldwork.views.map.SpotMapView
import com.archaeologicalfieldwork.views.settings.SettingsView
import com.archaeologicalfieldwork.views.spot.SpotView
import com.archaeologicalfieldwork.views.spotList.SpotListView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2
val IMAGE_TAKE = 3

enum class VIEW {
    LOCATION, SPOT, MAPS, LIST, LOGIN, SETTINGS
}

open abstract class BaseView : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, SpotListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.SPOT -> intent = Intent(this, SpotView::class.java)
            VIEW.MAPS -> intent = Intent(this, SpotMapView::class.java)
            VIEW.LIST -> intent = Intent(this, SpotListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showSpot(spot: SpotModel) {}
    open fun showSpots(spots: List<SpotModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
    open fun showLocation(location: Location) {}
}