package com.msaifurrijaal.tokoonline.presentation.sell

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.model.LatLng
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.ActivitySellBinding
import com.msaifurrijaal.tokoonline.presentation.location.LocationActivity
import com.msaifurrijaal.tokoonline.presentation.uploadphoto.UploadPhotoActivity
import com.msaifurrijaal.tokoonline.utils.convertToAddress
import com.msaifurrijaal.tokoonline.utils.startActivity

class SellActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySellBinding
    private var location: LatLng? = null

    private val startMapResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            val mLocation = data?.getParcelableExtra<LatLng>(LocationActivity.EXTRA_LOCATION)
            if (mLocation != null) {
                location = mLocation
                val address = location?.convertToAddress(this)
                binding.etAddressSell.setText(address.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
    }

    private fun onAction() {
        binding.btnMapSell.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startMapResult.launch(intent)
        }

        binding.btnSubmitSell.setOnClickListener {
            startActivity<UploadPhotoActivity>()
            finish()
        }

        binding.tbSell.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}