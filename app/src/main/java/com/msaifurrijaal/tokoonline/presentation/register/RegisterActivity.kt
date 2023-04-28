package com.msaifurrijaal.tokoonline.presentation.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivityRegisterBinding
import com.msaifurrijaal.tokoonline.utils.toast

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
        binding.btnRegister.setOnClickListener { toast("Register") }

        binding.tbRegister.setNavigationOnClickListener { onBackPressed() }
    }

    // untuk menambahkan action bar pada ui
    private fun initActionBar() {
        setSupportActionBar(binding.tbRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}