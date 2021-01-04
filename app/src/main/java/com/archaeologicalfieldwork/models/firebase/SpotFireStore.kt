package com.archaeologicalfieldwork.models.firebase


import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import com.archaeologicalfieldwork.models.SpotModel
import com.archaeologicalfieldwork.models.SpotStore

class SpotFireStore(val context: Context) : SpotStore, AnkoLogger {

    val spots = ArrayList<SpotModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

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

    }

    override fun delete(spot: SpotModel) {
        db.child("users").child(userId).child("spots").child(spot.fbId).removeValue()
        spots.remove(spot)
    }

    override fun clear() {
        spots.clear()
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
        spots.clear()
        db.child("users").child(userId).child("spots").addListenerForSingleValueEvent(valueEventListener)
    }
}