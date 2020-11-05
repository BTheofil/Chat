package hu.nagyhazi.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import hu.nagyhazi.R
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment: Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        login_btn.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        register_btn.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }
}