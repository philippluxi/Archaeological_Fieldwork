package com.archaeologicalfieldwork.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class SpotModel(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var fbId: String = "",
    var title: String = "",
    var description: String = "",
    var image: String = "",
    var visited: Boolean = false,
    var dateVisited: String = "Not Visited",
    var favorite: Boolean,
    var rating: Float = 0f,
    @Embedded var location: Location = Location()
) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 15f
) : Parcelable