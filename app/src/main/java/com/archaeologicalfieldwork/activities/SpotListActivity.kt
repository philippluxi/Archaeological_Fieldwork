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
import org.jetbrains.anko.startActivityForResult


class SpotListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spot_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SpotAdapter(app.spots)
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
}

class SpotAdapter constructor(private var spots: List<SpotModel>) :
    RecyclerView.Adapter<SpotAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_spot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val spot = spots[holder.adapterPosition]
        holder.bind(spot)
    }

    override fun getItemCount(): Int = spots.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(spot: SpotModel) {
            itemView.spotTitle.text = spot.title
            itemView.spotDescription.text = spot.desription
        }
    }
}