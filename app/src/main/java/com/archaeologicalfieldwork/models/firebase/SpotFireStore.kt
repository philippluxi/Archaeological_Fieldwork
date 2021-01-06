package com.archaeologicalfieldwork.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.archaeologicalfieldwork.helpers.readImageFromPath
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.models.SpotStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class SpotFireStore(val context: Context) : SpotStore, AnkoLogger {

    val spots = ArrayList<SpotModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<SpotModel> {
        return spots
    }

    override fun findById(id: Long): SpotModel? {
        val foundSpot: SpotModel? = spots.find { p -> p.id == id }
        return foundSpot
    }

    override fun create(spot: SpotModel) {
        val key = db.child("users").child(userId).child("spots").push().key
        key?.let {
            spot.fbId = key
            spots.add(spot)
            db.child("users").child(userId).child("spots").child(key).setValue(spot)
            updateImage(spot)
        }
    }

    override fun update(spot: SpotModel) {
        var foundSpot: SpotModel? = spots.find { p -> p.fbId == spot.fbId }
        if (foundSpot != null) {
            foundSpot.title = spot.title
            foundSpot.description = spot.description
            foundSpot.image = spot.image
            foundSpot.location = spot.location
        }

        db.child("users").child(userId).child("spots").child(spot.fbId).setValue(spot)
        if ((spot.image.length) > 0 && (spot.image[0] != 'h')) {
            updateImage(spot)
        }
    }

    override fun delete(spot: SpotModel) {
        db.child("users").child(userId).child("spots").child(spot.fbId).removeValue()
        spots.remove(spot)
    }

    override fun clear() {
        spots.clear()
    }

    fun updateImage(spot: SpotModel) {
        if (spot.image != "") {
            val fileName = File(spot.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, spot.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        spot.image = it.toString()
                        db.child("users").child(userId).child("spots").child(spot.fbId)
                            .setValue(spot)
                    }
                }
            }
        }
    }

    fun fetchSpots(spotsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(spots) { it.getValue<SpotModel>(SpotModel::class.java) }
                spotsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        spots.clear()
        db.child("users").child(userId).child("spots")
            .addListenerForSingleValueEvent(valueEventListener)
    }
}