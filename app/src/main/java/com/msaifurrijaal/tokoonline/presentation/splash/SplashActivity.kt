package com.msaifurrijaal.tokoonline.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.presentation.login.LoginActivity
import com.msaifurrijaal.tokoonline.utils.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayAndGoToLogin()
    }

    private fun delayAndGoToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, 2000)
    }
}