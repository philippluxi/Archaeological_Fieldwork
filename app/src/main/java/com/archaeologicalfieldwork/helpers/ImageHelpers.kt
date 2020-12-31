package com.archaeologicalfieldwork.helpers

import android.app.Activity
import android.content.Intent
import com.archaeologicalfieldwork.R


fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    val chooser = Intent.createChooser(intent, R.string.select_spot_image.toString())
    parent.startActivityForResult(chooser, id)
}