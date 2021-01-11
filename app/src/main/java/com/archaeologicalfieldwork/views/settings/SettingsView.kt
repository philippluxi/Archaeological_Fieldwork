package com.archaeologicalfieldwork.views.settings

import android.os.Bundle
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast

class SettingsView : BaseView() {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init(toolbarSettings, true)

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

        // Initialize Current Mail Text View
        current_mail.text = presenter.getCurrentMail()

        // Handle Update Mail
        btnSetNewMail.setOnClickListener {
            val newMailAdress = new_mail.text.toString()
            if (newMailAdress == "") {
                toast("No new email adress provided")
            } else {
                presenter.doUpdateMail(newMailAdress)
            }
        }

        // Handle Update Password
        btnSetNewPassword.setOnClickListener {
            val newPassword = new_password.text.toString()
            if (newPassword == "") {
                toast("No new password provided")
            } else {
                presenter.doUpdatePassword(newPassword)
            }
        }

        // Initialize Statistics
        number_all_spots.text = presenter.getNumberAll()
        number_favorite_spots.text = presenter.getNumberFavorites()
        number_visited_spots.text = presenter.getNumberVisited()
    }
}
