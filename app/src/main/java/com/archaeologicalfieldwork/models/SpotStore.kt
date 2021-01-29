package com.archaeologicalfieldwork.models

import android.graphics.Bitmap

interface SpotStore {
    fun findAll(): List<SpotModel>
    fun findStarred(): List<SpotModel>
    fun findVisited(): List<SpotModel>
    fun findById(id: Long): SpotModel?
    fun create(spot: SpotModel)
    fun update(spot: SpotModel)
    fun delete(spot: SpotModel)
    fun clear()
    open fun updateImageFromCam(bitmap: Bitmap, spot: SpotModel)
}