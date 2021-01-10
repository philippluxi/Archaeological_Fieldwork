package com.archaeologicalfieldwork.views.settings


import com.archaeologicalfieldwork.models.firebase.SpotFireStore
import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast

class SettingsPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentMail(): String? {
        if (auth.currentUser != null) {
            val current_mail = auth.currentUser!!.email
            return current_mail
        } else {
            return ""
            view?.toast("Not User found")
        }
    }

    fun getNumberAll(): String {
        return app.spots.findAll().size.toString()
    }

    fun getNumberFavorites(): String {
        return app.spots.findStarred().size.toString()
    }

    fun getNumberVisited(): String {
        return app.spots.findVisited().size.toString()
    }

}