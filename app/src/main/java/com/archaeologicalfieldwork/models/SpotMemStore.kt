package com.archaeologicalfieldwork.models

import java.util.ArrayList
import org.jetbrains.anko.info
import org.jetbrains.anko.AnkoLogger

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SpotMemStore : SpotStore, AnkoLogger {

    val spots = ArrayList<SpotModel>()

    override fun findAll(): List<SpotModel> {
        return spots
    }

    override fun findById(id: Long): SpotModel? {
        val foundSpot: SpotModel? = spots.find { it.id == id }
        return foundSpot
    }

    override fun create(spot: SpotModel) {
        spot.id = getId()
        spots.add(spot)
        logAll()
    }

    override fun update(spot: SpotModel) {
        var foundSpot: SpotModel? = spots.find { spot_iterator -> spot_iterator.id == spot.id }
        if (foundSpot != null) {
            foundSpot.title = spot.title
            foundSpot.description = spot.description
            foundSpot.image = spot.image
            foundSpot.lat = spot.lat
            foundSpot.lng = spot.lng
            foundSpot.zoom = spot.zoom
            logAll()
        }
    }

    override fun delete(spot: SpotModel) {
        spots.remove(spot)
    }

    fun logAll() {
        spots.forEach { info("${it}") }
    }
}