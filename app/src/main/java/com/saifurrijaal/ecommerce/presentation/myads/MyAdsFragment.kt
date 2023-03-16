package com.saifurrijaal.ecommerce.presentation.myads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.saifurrijaal.ecommerce.R
import com.saifurrijaal.ecommerce.databinding.FragmentMyAdsBinding


class MyAdsFragment : Fragment() {

    private var _binding: FragmentMyAdsBinding? = null
    private val binding: FragmentMyAdsBinding get() = _binding!!
    private lateinit var myAdsAdapter: MyAdsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAdsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMyAds()
        onAction()
    }

    private fun onAction() {
        myAdsAdapter.onClick {
            val popUpMenu = PopupMenu(context, it)
            popUpMenu.menuInflater.inflate(R.menu.config_my_ads_menu, popUpMenu.menu)

            popUpMenu.setOnMenuItemClickListener {
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
            popUpMenu.show()
        }
    }

    private fun initMyAds() {
        myAdsAdapter = MyAdsAdapter()
        binding.rvMyAds.adapter = myAdsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}