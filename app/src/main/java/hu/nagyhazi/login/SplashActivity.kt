package hu.nagyhazi.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import hu.nagyhazi.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({

            startActivity(Intent(this,StartActivity::class.java))

            finish()
        }, SPLASH_TIME_OUT)
    }
}