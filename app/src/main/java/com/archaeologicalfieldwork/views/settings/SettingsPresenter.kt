package com.archaeologicalfieldwork.views.settings


import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
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

    fun doUpdateMail(newMail: String) {
        if (auth.currentUser != null) {
            auth.currentUser!!.updateEmail(newMail).addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    view?.toast("Update Mail Successful")
                } else {
                    view?.toast("Update Mail Failed: ${task.exception?.message}")
                }
            }
        } else {
            view?.toast("No Firebase Session found")
        }
    }

    fun doUpdatePassword(newPassword: String) {
        if (auth.currentUser != null) {
            auth.currentUser!!.updatePassword(newPassword).addOnCompleteListener(view!!) { task ->
                if (task.isSuccessful) {
                    view?.toast("Update Password Successful")
                } else {
                    view?.toast("Update Password Failed: ${task.exception?.message}")
                }
            }
        } else {
            view?.toast("No Firebase Session found")
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