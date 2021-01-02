package com.archaeologicalfieldwork.views.spot

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.helpers.readImageFromPath
import com.archaeologicalfieldwork.views.*

class SpotView : BaseView(), AnkoLogger {

    lateinit var presenter: SpotPresenter
    var spot = SpotModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot)

        init(toolbarAdd)

        presenter = initPresenter(SpotPresenter(this)) as SpotPresenter

        // Handle Add Image Button Press
        btnChooseImage.setOnClickListener {
            presenter.cacheSpot(spotTitle.text.toString(),spotDescription.text.toString())
            presenter.doSelectImage()
        }

        // Handle Set Location Button Press
        btnSetLocation.setOnClickListener {
            presenter.cacheSpot(spotTitle.text.toString(),spotDescription.text.toString())
            presenter.doSetLocation()
        }
    }

    override fun showSpot(spot: SpotModel) {
        spotTitle.setText(spot.title)
        spotDescription.setText(spot.description)
        spotImage.setImageBitmap(readImageFromPath(this, spot.image))
        if (spot.image != null) {
            btnChooseImage.setText(R.string.change_spot_image)
        }
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
}