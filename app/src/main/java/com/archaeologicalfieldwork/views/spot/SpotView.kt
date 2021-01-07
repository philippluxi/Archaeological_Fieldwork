package com.archaeologicalfieldwork.views.spot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BaseView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class SpotView : BaseView(), AnkoLogger {

    lateinit var presenter: SpotPresenter
    var spot = SpotModel()

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

        init(toolbarAdd, true)

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

        // Handle Visited Checkbox
        visited_checkBox.setOnClickListener {
            presenter.doSetVisited(visited_checkBox.isChecked)
            if (!visited_checkBox.isChecked) {
                val dateVisited_textview: TextView = findViewById(R.id.date_visited)
                dateVisited_textview.visibility = View.INVISIBLE
            }
        }
    }

    override fun showSpot(spot: SpotModel) {
        if (spotTitle.text.isEmpty()) spotTitle.setText(spot.title)
        if (spotDescription.text.isEmpty()) spotDescription.setText(spot.description)
        visited_checkBox.isChecked = spot.visited
        if (spot.visited) {
            val dateVisited_textview: TextView = findViewById(R.id.date_visited)
            dateVisited_textview.text = spot.dateVisited
            dateVisited_textview.visibility = View.VISIBLE
        } else {
            val dateVisited_textview: TextView = findViewById(R.id.date_visited)
            dateVisited_textview.visibility = View.INVISIBLE
        }
        Glide.with(this).load(spot.image).into(spotImage)
        if (spot.image != null) {
            btnChooseImage.setText(R.string.change_spot_image)
        }
        this.showLocation(spot.location)
    }

    override fun showLocation(loc: Location) {
        lat.setText("%.6f".format(loc.lat))
        lng.setText("%.6f".format(loc.lng))
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