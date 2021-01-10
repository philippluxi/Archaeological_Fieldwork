package com.archaeologicalfieldwork.views.settings

import android.os.Bundle
import android.view.View
import com.archaeologicalfieldwork.R
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.views.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast

class SettingsView : BaseView() {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init(toolbar, true)

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

        // Initialize Current Mail Text View
        current_mail.text = presenter.getCurrentMail()

        // Initialize Statistics
        number_all_spots.text = presenter.getNumberAll()
        number_favorite_spots.text = presenter.getNumberFavorites()
        number_visited_spots.text = presenter.getNumberVisited()

    }
}
