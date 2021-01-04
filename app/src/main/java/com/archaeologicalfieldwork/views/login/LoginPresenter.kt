package com.archaeologicalfieldwork.views.login

import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    fun doLogin(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }

    fun doSignUp(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }
}