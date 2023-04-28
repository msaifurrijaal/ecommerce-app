package com.msaifurrijaal.tokoonline.presentation.myads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.databinding.FragmentMyAdsBinding

class MyAdsFragment : Fragment() {

    private var _binding: FragmentMyAdsBinding? = null
    private val binding get() = _binding!!
    private lateinit var myAdsAdapter: MyAdsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAdsBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
        * init all data
        * */
        initMyAds()
        onAction()
    }

    private fun onAction() {
        myAdsAdapter.onClick {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.config_my_ads_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_edit -> {
                        Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_manage_photos -> {
                        Toast.makeText(context, "Manage Photos", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_delete -> {
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }

            popupMenu.show()
        }
    }

    private fun initMyAds() {
        myAdsAdapter = MyAdsAdapter()

        binding.rvMyAds.adapter = myAdsAdapter
    }
}