package com.archaeologicalfieldwork.views.spot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BaseView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class SpotView : BaseView(), AnkoLogger {

    lateinit var presenter: SpotPresenter
    var spot = SpotModel()
    var currentSpot_notes = String()
    var currentSpot_title = String()

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

        // Handle Add Notes Button pressed
        btnAddNotes.setOnClickListener {
            createNotesPopUp()
        }

        // Handle Visited Checkbox
        visited_checkBox.setOnClickListener {
            presenter.doSetVisited(visited_checkBox.isChecked)
            // Set TextView of Date invisible, if Box is unchecked
            if (!visited_checkBox.isChecked) {
                val dateVisited_textview: TextView = findViewById(R.id.date_visited)
                dateVisited_textview.visibility = View.INVISIBLE
            }
        }

        // Handle Rating Click
        rate_spot.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            presenter.doSetRating(rating)
        }
    }

    override fun showSpot(spot: SpotModel) {
        //Title and Description
        if (spotTitle.text.isEmpty()) spotTitle.setText(spot.title)
        if (spotDescription.text.isEmpty()) spotDescription.setText(spot.description)

        //CheckBox Visited and Date TextView
        visited_checkBox.isChecked = spot.visited
        if (spot.visited) {
            val dateVisited_textview: TextView = findViewById(R.id.date_visited)
            dateVisited_textview.text = spot.dateVisited
            dateVisited_textview.visibility = View.VISIBLE
        } else {
            val dateVisited_textview: TextView = findViewById(R.id.date_visited)
            dateVisited_textview.visibility = View.INVISIBLE
        }

        // Setup for Notes PopUp
        if (spot.notes != "") {
            btnAddNotes.setText(R.string.btn_Edit_Notes)
        }
        currentSpot_title = spot.title
        currentSpot_notes = spot.notes

        // Rating
        rate_spot.rating = spot.rating

        // Image
        Glide.with(this).load(spot.image).into(spotImage)
        if (spot.image != null) {
            btnChooseImage.setText(R.string.change_spot_image)
        }

        // Location
        this.showLocation(spot.location)
    }

    override fun showLocation(loc: Location) {
        lat.text = "%.6f".format(loc.lat)
        lng.text = "%.6f".format(loc.lng)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_spot, menu)
        if (presenter.edit) {
            menu.getItem(0).isVisible = true    // Delete
            menu.getItem(3).isVisible = true    // Share
        }
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
            R.id.item_share -> {
                presenter.doShare()
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

    fun createNotesPopUp() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.popup_notes, null)
        val notes = dialogLayout.findViewById<EditText>(R.id.notespopup_notes)
        var popup_spotTitle = dialogLayout.findViewById<TextView>(R.id.notespopup_NotesSpotTitle)

        with(builder) {
            setTitle("Notes")

            // Setup with stored values
            popup_spotTitle.text = spotTitle.text
            if (currentSpot_notes != "") {
                notes.setText(currentSpot_notes)
            }

            // Set Buttons
            setPositiveButton("Finish") { dialogLayout, which ->
                val notes = notes.text.toString()
                presenter.doHandleNotes(notes)
            }
            setNegativeButton("Cancel") { dialogLayout, which ->
                //Do Nothing but close on Cancel
            }

            setView(dialogLayout)
            show()
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