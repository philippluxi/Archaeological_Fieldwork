package com.archaeologicalfieldwork.views.spotList

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_spot_list.*

class SpotListView : BaseView(), SpotListener {

    lateinit var presenter: SpotListPresenter
    lateinit var navToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)

        super.init(toolbar, false)

        navToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(navToggle)
        navToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            navItemListener(it)
        }

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
        var menuItemSearch = menu!!.findItem(R.id.searchView)

        val searchView = menuItemSearch.getActionView() as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(searchString: String?): Boolean {
                presenter.loadSpotsForSearch(searchString as String)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (navToggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.item_add -> presenter.doaddSpot()
            R.id.item_map -> presenter.doShowSpotsMap()
            R.id.item_all -> presenter.doShowAllSpots()
            R.id.item_favorites -> presenter.doShowFavoriteSpots()
            R.id.item_visited -> presenter.doShowVisitedSpots()
            R.id.item_settings -> presenter.doShowSettings()
            R.id.item_logout -> presenter.doLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    fun navItemListener(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doaddSpot()
            R.id.item_map -> presenter.doShowSpotsMap()
            R.id.item_all -> presenter.doShowAllSpots()
            R.id.item_favorites -> presenter.doShowFavoriteSpots()
            R.id.item_visited -> presenter.doShowVisitedSpots()
            R.id.item_settings -> presenter.doShowSettings()
            R.id.item_logout -> presenter.doLogout()
        }
        return true
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