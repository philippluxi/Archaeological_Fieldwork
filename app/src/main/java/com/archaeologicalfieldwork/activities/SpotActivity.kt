package com.archaeologicalfieldwork.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_spot.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.R

class SpotActivity : AppCompatActivity(), AnkoLogger {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    info("Spot Activity started..")
    setContentView(R.layout.activity_spot)

    btnAddSpot.setOnClickListener() {
      val spotTitel = spotTitle.text.toString()
      if (spotTitel.isNotEmpty()) {
        info("add Button pressed: ${spotTitle}")
      } else {
        toast("Please Enter a title")
      }
    }
  }
}