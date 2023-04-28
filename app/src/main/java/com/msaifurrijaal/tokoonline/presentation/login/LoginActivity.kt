package com.msaifurrijaal.tokoonline.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msaifurrijaal.tokoonline.databinding.ActivityLoginBinding
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.presentation.register.RegisterActivity
import com.msaifurrijaal.tokoonline.utils.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.btnLogin.setOnClickListener { startActivity<MainActivity>() }

        binding.btnRegisterLogin.setOnClickListener { startActivity<RegisterActivity>() }
    }
}