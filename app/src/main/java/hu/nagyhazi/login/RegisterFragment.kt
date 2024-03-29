package hu.nagyhazi.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import hu.nagyhazi.R
import hu.nagyhazi.model.User
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var navController: NavController
    private var database = FirebaseDatabase.getInstance()
    private var mDatabase: DatabaseReference = database.getReference("users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        submit.setOnClickListener {
            initRegister(view)
        }
    }

    private fun initRegister(view: View) {
        val name = logName.text.toString()
        val email = logEmail.text.toString()
        val pw = logPw.text.toString()
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

                    addUserDatabase(name,email)

                    Snackbar.make(view, "Success register", Snackbar.LENGTH_LONG).show()
                    navController.navigate(R.id.action_registerFragment_to_frontFragment)
                }
                .addOnFailureListener{
                    Snackbar.make(view, "Fail to create user: ${it.message}", Snackbar.LENGTH_LONG).show()
                    return@addOnFailureListener
                }
            }
    }

    private fun addUserDatabase(newUserName: String, email: String) {
        val inCloud = mDatabase.child(newUserName)
        val temp = arrayListOf("start")
        val users = User(newUserName,email,temp)
        //val users: MutableMap<String, User> = HashMap()
        //users.add(User(newUserName,email,temp))
        //users["Info"] = User(newUserName, email, temp)
        inCloud.setValue(users)
    }
}