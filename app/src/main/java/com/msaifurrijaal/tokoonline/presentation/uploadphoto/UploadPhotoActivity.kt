package com.msaifurrijaal.tokoonline.presentation.uploadphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivityUploadPhotoBinding
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.utils.startActivity

class UploadPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadPhotoBinding
    private lateinit var uploadPhotoAdapter: UploadPhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUploadPhotoAdapter()
        onAction()
    }

    private fun onAction() {
        binding.btnUploadPhoto.setOnClickListener {
            startActivity<MainActivity>()
            finishAffinity()
        }

        binding.tbUploadPhoto.setOnClickListener {
            startActivity<MainActivity>()
            finishAffinity()
        }
    }

    private fun initUploadPhotoAdapter() {
        uploadPhotoAdapter = UploadPhotoAdapter()
        binding.rvUploadPhoto.adapter = uploadPhotoAdapter
    }

    companion object {
        const val EXTRA_ADS = "extra_ads"
        const val EXTRA_IS_EDIT = "extra_is_edit"
    }
}