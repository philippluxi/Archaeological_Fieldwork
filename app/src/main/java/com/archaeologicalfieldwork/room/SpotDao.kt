package com.archaeologicalfieldwork.room


import androidx.room.*
import com.archaeologicalfieldwork.models.SpotModel

@Dao
interface SpotDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(spot: SpotModel)

    @Query("SELECT * FROM SpotModel")
    fun findAll(): List<SpotModel>

    @Query("select * from SpotModel where favorite = 'true'")
    fun findStarred(): List<SpotModel>

    @Query("select * from SpotModel where visited = 'true'")
    fun findVisited(): List<SpotModel>

    @Query("select * from SpotModel where id = :id")
    fun findById(id: Long): SpotModel

    @Update
    fun update(spot: SpotModel)

    @Delete
    fun deleteSpot(spot: SpotModel)
}