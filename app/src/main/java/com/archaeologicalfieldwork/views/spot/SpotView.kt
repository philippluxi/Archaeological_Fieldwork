package com.archaeologicalfieldwork.views.spot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_spot.*
import kotlinx.android.synthetic.main.activity_spot.btnChooseImage
import org.jetbrains.anko.toast
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.views.*
import com.google.android.gms.maps.GoogleMap
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.helpers.readImageFromPath

class SpotView : BaseView(), AnkoLogger {

    lateinit var presenter: SpotPresenter
    var spot = SpotModel()

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

        init(toolbarAdd)

        presenter = initPresenter(SpotPresenter(this)) as SpotPresenter

        // Setup MapView
        spotLocation.onCreate(savedInstanceState)
        spotLocation.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        // Handle Add Image Button Press
        btnChooseImage.setOnClickListener {
            presenter.cacheSpot(spotTitle.text.toString(), spotDescription.text.toString())
            presenter.doSelectImage()
        }
    }

    override fun showSpot(spot: SpotModel) {
        if (spotTitle.text.isEmpty()) spotTitle.setText(spot.title)
        if (spotDescription.text.isEmpty()) spotDescription.setText(spot.description)
        spotImage.setImageBitmap(readImageFromPath(this, spot.image))
        if (spot.image != null) {
            btnChooseImage.setText(R.string.change_spot_image)
        }
        lat.setText("%.6f".format(spot.lat))
        lng.setText("%.6f".format(spot.lng))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_spot, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (spotTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_spot_title)
                } else {
                    presenter.doAddOrSave(
                        spotTitle.text.toString(),
                        spotDescription.text.toString()
                    )
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        spotLocation.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        spotLocation.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        spotLocation.onPause()
    }

    override fun onResume() {
        super.onResume()
        spotLocation.onResume()
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        spotLocation.onSaveInstanceState(outState)
    }
}