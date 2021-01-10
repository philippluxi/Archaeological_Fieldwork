package com.archaeologicalfieldwork.models

interface SpotStore {
    fun findAll(): List<SpotModel>
    fun findStarred(): List<SpotModel>
    fun findVisited(): List<SpotModel>
    fun findById(id: Long): SpotModel?
    fun create(spot: SpotModel)
    fun update(spot: SpotModel)
    fun delete(spot: SpotModel)
    fun clear()
}