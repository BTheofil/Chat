package hu.nagyhazi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import hu.nagyhazi.R
import hu.nagyhazi.viewmodel.LoginViewModel

class StartActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initFragment()
    }

    private fun initFragment() {
        val welcomeFragment = WelcomeFragment()
        welcomeFragment.loginViewModel = loginViewModel
    }

}