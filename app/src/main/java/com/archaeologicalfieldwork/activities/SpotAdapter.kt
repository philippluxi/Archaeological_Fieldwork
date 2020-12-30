package com.archaeologicalfieldwork.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import kotlinx.android.synthetic.main.card_spot.view.*


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
            itemView.spotTitle_Card.text = spot.title
            itemView.spotDescription_Card.text = spot.desription
        }
    }
}