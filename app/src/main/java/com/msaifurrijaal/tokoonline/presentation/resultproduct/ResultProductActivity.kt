package com.msaifurrijaal.tokoonline.presentation.resultproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivityResultProductBinding

class ResultProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}