package com.archaeologicalfieldwork.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.main.MainApp
import com.archaeologicalfieldwork.models.SpotModel
import kotlinx.android.synthetic.main.activity_spot_list.*
import kotlinx.android.synthetic.main.card_spot.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult


class SpotListActivity : AppCompatActivity(), SpotListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SpotAdapter(app.spots.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<SpotActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSpotClick(spot: SpotModel) {
        startActivityForResult(intentFor<SpotActivity>(), 0)
    }
}