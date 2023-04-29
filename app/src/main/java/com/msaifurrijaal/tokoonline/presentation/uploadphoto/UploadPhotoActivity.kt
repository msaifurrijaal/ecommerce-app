package com.msaifurrijaal.tokoonline.presentation.uploadphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivityUploadPhotoBinding

class UploadPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}