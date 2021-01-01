package com.archaeologicalfieldwork.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import java.util.*
import com.archaeologicalfieldwork.helpers.*

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

    override fun create(spot: SpotModel) {
        spot.id = generateRandomId()
        spots.add(spot)
        serialize()
    }


    override fun update(spot: SpotModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(spots, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        spots = Gson().fromJson(jsonString, listType)
    }
}