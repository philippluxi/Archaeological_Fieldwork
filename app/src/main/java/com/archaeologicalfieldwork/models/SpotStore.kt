package com.archaeologicalfieldwork.models

interface SpotStore {
    fun findAll(): List<SpotModel>
    fun create(spot: SpotModel)
    fun update(spot: SpotModel)
}