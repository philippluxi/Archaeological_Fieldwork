package com.archaeologicalfieldwork.room

import android.content.Context
import androidx.room.Room
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.models.SpotStore

class SpotStoreRoom(val context: Context) : SpotStore {

    var dao: SpotDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.spotDao()
    }

    override fun findAll(): List<SpotModel> {
        return dao.findAll()
    }

    override fun findStarred(): List<SpotModel> {
        return dao.findStarred()
    }

    override fun findVisited(): List<SpotModel> {
        return dao.findVisited()
    }

    override fun findById(id: Long): SpotModel? {
        return dao.findById(id)
    }

    override fun create(spot: SpotModel) {
        dao.create(spot)
    }

    override fun update(spot: SpotModel) {
        dao.update(spot)
    }

    override fun delete(spot: SpotModel) {
        dao.deleteSpot(spot)
    }

    override fun clear() {
    }
}

