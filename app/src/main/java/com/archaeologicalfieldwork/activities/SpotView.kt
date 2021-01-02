package com.archaeologicalfieldwork.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.Location
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.helpers.readImage
import com.archaeologicalfieldwork.helpers.showImagePicker
import com.archaeologicalfieldwork.helpers.readImageFromPath

class SpotView : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: SpotPresenter
    var spot = SpotModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = SpotPresenter(this)

        // Handle Add Button Press
        btnAddSpot.setOnClickListener {
            if (spotTitle.text.toString().isEmpty()) {
                toast(R.string.enter_spot_title)
            } else {
                presenter.doAddOrSave(spotTitle.text.toString(), spotDescription.text.toString())
            }
        }

        // Handle Add Image Button Press
        btnChooseImage.setOnClickListener { presenter.doSelectImage() }

        // Handle Set Location Button Press
        btnSetLocation.setOnClickListener { presenter.doSetLocation() }
    }

    fun showSpot(spot: SpotModel) {
        spotTitle.setText(spot.title)
        spotDescription.setText(spot.description)
        spotImage.setImageBitmap(readImageFromPath(this, spot.image))
        if (spot.image != null) {
            btnChooseImage.setText(R.string.change_spot_image)
        }
        btnAddSpot.setText(R.string.button_save_spot)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_spot, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
}