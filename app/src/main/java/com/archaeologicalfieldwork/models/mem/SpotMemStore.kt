package com.archaeologicalfieldwork.models.mem

import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.models.SpotStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

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
            foundSpot.visited = spot.visited
            foundSpot.dateVisited = spot.dateVisited
            foundSpot.favorite = spot.favorite
            foundSpot.rating = spot.rating
            foundSpot.location = spot.location
            logAll()
        }
    }

    override fun delete(spot: SpotModel) {
        spots.remove(spot)
    }

    override fun clear() {
        spots.clear()
    }

    fun logAll() {
        spots.forEach { info("${it}") }
    }
}