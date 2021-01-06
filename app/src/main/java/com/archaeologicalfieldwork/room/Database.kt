package com.archaeologicalfieldwork.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.archaeologicalfieldwork.models.SpotModel

@Database(entities = arrayOf(SpotModel::class), version = 3, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun spotDao(): SpotDao
}