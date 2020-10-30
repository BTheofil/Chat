package hu.nagyhazi.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.nagyhazi.R
import hu.nagyhazi.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_welcome.*

class RegisterFragment : Fragment() {

    private lateinit var navController: NavController
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_register, container, false)

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginViewModel = LoginViewModel(requireActivity().application)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        submit.setOnClickListener {
            initRegister(view)
        }
    }

    private fun initRegister(view: View) {
        val email = etEmail.text.toString()
        val pw = etPassword.text.toString()
        val rePw = rePassword.text.toString()

        if (email.isEmpty() || pw.isEmpty() || rePw.isEmpty()){
            Snackbar.make(view, "Enter your email and password", Snackbar.LENGTH_LONG).show()
            return
        }

        if (pw != rePw){
            Snackbar.make(view, "Not equals password", Snackbar.LENGTH_LONG).show()
            return
        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, rePw)
                .addOnSuccessListener  { result ->
                    val firebaseUser = result.user
                    val profileChangeRequest = UserProfileChangeRequest.Builder()
                        .setDisplayName(firebaseUser?.email?.substringBefore('@'))
                        .build()
                    firebaseUser?.updateProfile(profileChangeRequest)

                    Snackbar.make(view, "Success register", Snackbar.LENGTH_LONG).show()

                    activity?.let{
                        val intent = Intent (it, MainActivity::class.java)
                        it.startActivity(intent)
                    }
                }
                .addOnFailureListener{
                    Snackbar.make(view, "Fail to create user: ${it.message}", Snackbar.LENGTH_LONG).show()
                    return@addOnFailureListener
                }
            }
    }

}