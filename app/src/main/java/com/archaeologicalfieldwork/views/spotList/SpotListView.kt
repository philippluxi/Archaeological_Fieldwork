package com.archaeologicalfieldwork.views.spotList

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_spot_list.*
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.activities.SpotAdapter
import com.archaeologicalfieldwork.activities.SpotListener
import com.archaeologicalfieldwork.models.SpotModel

class SpotListView : AppCompatActivity(), SpotListener {

    lateinit var presenter: SpotListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = SpotListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            SpotAdapter(presenter.getSpots(), this)
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSpotClick(spot: SpotModel) {
        presenter.doeditSpot(spot)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}