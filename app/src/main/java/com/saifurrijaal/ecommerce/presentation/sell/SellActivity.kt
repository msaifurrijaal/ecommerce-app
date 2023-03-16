package com.saifurrijaal.ecommerce.presentation.sell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saifurrijaal.ecommerce.R
import com.saifurrijaal.ecommerce.databinding.ActivitySellBinding

class SellActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}