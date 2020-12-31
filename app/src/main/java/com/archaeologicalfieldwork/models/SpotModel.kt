package com.archaeologicalfieldwork.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpotModel(
    var title: String = "",
    var desription: String = ""
) : Parcelable