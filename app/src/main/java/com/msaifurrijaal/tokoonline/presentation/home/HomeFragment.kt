package com.msaifurrijaal.tokoonline.presentation.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.model.LatLng
import com.msaifurrijaal.tokoonline.databinding.FragmentHomeBinding
import com.msaifurrijaal.tokoonline.presentation.detailproduct.DetailProductActivity
import com.msaifurrijaal.tokoonline.presentation.location.LocationActivity
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.utils.convertToAddress
import com.msaifurrijaal.tokoonline.utils.startActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recommendProductAdapter: RecommendProductAdapter
    private var location: LatLng? = null

    private val startMapResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            val mLocation = data?.getParcelableExtra<LatLng>(LocationActivity.EXTRA_LOCATION)
            if (mLocation != null) {
                location = mLocation
                val address = location?.convertToAddress(requireContext())
                binding.tvCurrentLocation.text = address.toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecommendProduct()
        onAction()
    }

    private fun onAction() {
        binding.btnSearchHome.setOnClickListener {  }

        binding.btnCurrentLocation.setOnClickListener {
            val intent = Intent(context, LocationActivity::class.java)
            startMapResult.launch(intent)
        }

        recommendProductAdapter.onClick {
            (activity as MainActivity).startActivity<DetailProductActivity>()
        }
    }

    private fun initRecommendProduct() {
        recommendProductAdapter = RecommendProductAdapter()
        binding.rvRecommendProductHome.adapter = recommendProductAdapter
    }
}