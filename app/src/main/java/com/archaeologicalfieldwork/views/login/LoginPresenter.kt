
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import com.archaeologicalfieldwork.models.firebase.SpotFireStore
import com.archaeologicalfieldwork.views.BasePresenter
import com.archaeologicalfieldwork.views.BaseView
import com.archaeologicalfieldwork.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: SpotFireStore? = null

    init {
        if (app.spots is SpotFireStore) {
            fireStore = app.spots as SpotFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchSpots {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.spots.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}