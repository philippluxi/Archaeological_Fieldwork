package com.archaeologicalfieldwork.views.spotList

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_spot_list.*

class SpotListView : BaseView(), SpotListener {

    lateinit var presenter: SpotListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)
        super.init(toolbar, false)

        presenter = initPresenter(SpotListPresenter(this)) as SpotListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadSpots()
    }

    override fun showSpots(spots: List<SpotModel>) {
        recyclerView.adapter = SpotAdapter(spots, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doaddSpot()
            R.id.item_map -> presenter.doShowSpotsMap()
            R.id.item_settings -> presenter.doShowSettings()
            R.id.item_logout -> presenter.doLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSpotClick(spot: SpotModel) {
        presenter.doeditSpot(spot)
    }

    override fun onFavoriteClick(spot: SpotModel, isFavorite: Boolean) {
        presenter.doHandleFavorite(spot, isFavorite)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadSpots()
        super.onActivityResult(requestCode, resultCode, data)
    }
}