package com.saifurrijaal.ecommerce.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saifurrijaal.ecommerce.R
import com.saifurrijaal.ecommerce.databinding.ActivityRegisterBinding
import com.saifurrijaal.ecommerce.utils.toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        onAction()
    }

    private fun onAction() {
        binding.btnRegister.setOnClickListener {
            toast("register")
        }

        binding.tbRegister.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initActionBar() {
        setSupportActionBar(binding.tbRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}