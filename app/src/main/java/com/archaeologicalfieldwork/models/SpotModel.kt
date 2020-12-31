package com.archaeologicalfieldwork.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpotModel(
    var id: Long = 0,
    var title: String = "",
    var desription: String = "",
    var image: String = ""
) : Parcelable