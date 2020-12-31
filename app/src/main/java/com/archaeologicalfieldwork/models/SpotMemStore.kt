package com.archaeologicalfieldwork.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.ArrayList

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class SpotMemStore : SpotStore, AnkoLogger {

    val spots = ArrayList<SpotModel>()

    override fun findAll(): List<SpotModel> {
        return spots
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
            foundSpot.desription = spot.desription
        }

    }

    fun logAll() {
        spots.forEach { info("${it}") }
    }
}