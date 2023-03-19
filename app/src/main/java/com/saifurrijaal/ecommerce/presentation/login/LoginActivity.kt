package com.saifurrijaal.ecommerce.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saifurrijaal.ecommerce.databinding.ActivityLoginBinding
import com.saifurrijaal.ecommerce.presentation.main.MainActivity
import com.saifurrijaal.ecommerce.presentation.register.RegisterActivity
import com.saifurrijaal.ecommerce.utils.startActivity

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