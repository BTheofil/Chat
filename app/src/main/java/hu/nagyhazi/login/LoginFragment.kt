package hu.nagyhazi.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import hu.nagyhazi.R
import hu.nagyhazi.view.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.logEmail
import kotlinx.android.synthetic.main.fragment_register.logPw

class LoginFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        btnLogin.setOnClickListener {
            initLogin(view)
        }
    }

    private fun initLogin(view: View) {
        val email = logEmail.text.toString()
        val pw = logPw.text.toString()

        if (email.isEmpty() || pw.isEmpty()){
            Snackbar.make(view, "Enter your email and password", Snackbar.LENGTH_LONG).show()
            return
        }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw)
                .addOnSuccessListener  {
                    Snackbar.make(view, "Success login", Snackbar.LENGTH_LONG).show()

                    activity?.let{
                        val intent = Intent (it, MainActivity::class.java)
                        it.startActivity(intent)
                    }
                }
                .addOnFailureListener{
                    Snackbar.make(view, "Fail to login user: ${it.message}", Snackbar.LENGTH_LONG).show()
                    return@addOnFailureListener
                }
    }

}