package com.archaeologicalfieldwork.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.ArrayList


class SpotMemStore : SpotStore, AnkoLogger {

    val spots = ArrayList<SpotModel>()

    override fun findAll(): List<SpotModel> {
        return spots
    }

    override fun create(spot: SpotModel) {
        spots.add(spot)
    }

    fun logAll() {
        spots.forEach { info("${it}") }
    }
}