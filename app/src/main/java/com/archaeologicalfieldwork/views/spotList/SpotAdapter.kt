package com.archaeologicalfieldwork.views.spotList

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_spot.view.*
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.helpers.readImageFromPath

interface SpotListener {
    fun onSpotClick(spot: SpotModel)
}

class SpotAdapter constructor(
    private var spots: List<SpotModel>,
    private val listener: SpotListener
) : RecyclerView.Adapter<SpotAdapter.MainHolder>() {

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
        holder.bind(spot, listener)
    }

    override fun getItemCount(): Int = spots.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(spot: SpotModel, listener: SpotListener) {
            itemView.spotTitle_Card.text = spot.title
            itemView.spotDescription_Card.text = spot.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, spot.image))
            itemView.setOnClickListener { listener.onSpotClick(spot) }
        }
    }
}