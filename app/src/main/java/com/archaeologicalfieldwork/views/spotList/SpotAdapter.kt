package com.archaeologicalfieldwork.views.spotList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.models.SpotModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_spot.view.*

interface SpotListener {
    fun onSpotClick(spot: SpotModel)
    fun onFavoriteClick(spot: SpotModel, isFavorite: Boolean)
}

class SpotAdapter constructor(
    private var spots: List<SpotModel>,
    private val listener: SpotListener
) : RecyclerView.Adapter<SpotAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
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
            Glide.with(itemView.context).load(spot.image).into(itemView.imageIcon)
            itemView.setOnClickListener { listener.onSpotClick(spot) }
            itemView.favorite.setOnClickListener {
                listener.onFavoriteClick(spot, itemView.favorite.isChecked)
                // Todo Change drawable accordingly
            }
        }
    }
}
