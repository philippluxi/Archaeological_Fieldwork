package com.archaeologicalfieldwork.models.json

import android.content.Context
import com.archaeologicalfieldwork.helpers.exists
import com.archaeologicalfieldwork.helpers.read
import com.archaeologicalfieldwork.helpers.write
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.models.SpotStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "spots.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<SpotModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SpotJSONStore : SpotStore, AnkoLogger {

    val context: Context
    var spots = mutableListOf<SpotModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SpotModel> {
        return spots
    }

    override fun findById(id: Long): SpotModel? {
        val foundSpot: SpotModel? = spots.find { it.id == id }
        return foundSpot
    }

    override fun create(spot: SpotModel) {
        spot.id = generateRandomId()
        spots.add(spot)
        serialize()
    }

    override fun update(spot: SpotModel) {
        val spotList = findAll() as ArrayList<SpotModel>
        var foundSpot: SpotModel? = spotList.find { s -> s.id == spot.id }
        if (foundSpot != null) {
            foundSpot.title = spot.title
            foundSpot.description = spot.description
            foundSpot.image = spot.image
            foundSpot.visited = spot.visited
            foundSpot.dateVisited = spot.dateVisited
            foundSpot.rating = spot.rating
            foundSpot.location = spot.location
        }
        serialize()
    }

    override fun delete(spot: SpotModel) {
        val foundSpot: SpotModel? = spots.find { it.id == spot.id }
        spots.remove(foundSpot)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(spots, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        spots = Gson().fromJson(jsonString, listType)
    }

    override fun clear() {
        spots.clear()
    }
}