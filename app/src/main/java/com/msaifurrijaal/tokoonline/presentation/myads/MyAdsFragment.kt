package com.msaifurrijaal.tokoonline.presentation.myads

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.data.hawkstorage.HawkStorage
import com.msaifurrijaal.tokoonline.data.model.Resource
import com.msaifurrijaal.tokoonline.data.model.product.ProductResponse
import com.msaifurrijaal.tokoonline.databinding.FragmentMyAdsBinding
import com.msaifurrijaal.tokoonline.presentation.login.LoginActivity
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.utils.gone
import com.msaifurrijaal.tokoonline.utils.invisible
import com.msaifurrijaal.tokoonline.utils.showDialogError
import com.msaifurrijaal.tokoonline.utils.showDialogLoading
import com.msaifurrijaal.tokoonline.utils.startActivity
import com.msaifurrijaal.tokoonline.utils.visible

class MyAdsFragment : Fragment() {

    private var _binding: FragmentMyAdsBinding? = null
    private val binding get() = _binding!!
    private lateinit var myAdsAdapter: MyAdsAdapter
    private var myAdsResponse: ProductResponse? = null
    private var isFirstPage = true
    private lateinit var myAdsViewModel: MyAdsViewModel
    private lateinit var token: String
    private var userId = 0
    private lateinit var dialogLoading: AlertDialog

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogLoading = showDialogLoading(requireContext())
        myAdsViewModel = ViewModelProvider(this).get(MyAdsViewModel::class.java)
        myAdsAdapter = MyAdsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
        * init all data
        * */
        getToken()
        initMyAds()
        onAction()
    }

    override fun onResume() {
        super.onResume()
        getDataMyAds(true)
    }

    private fun getDataMyAds(isSwipe: Boolean) {
        myAdsViewModel.getMyAds(token, isSwipe, userId)
            .observe(viewLifecycleOwner){ state ->
                when(state){
                    Resource.Empty -> {
                        binding.swipeMyAds.isRefreshing = false
                        binding.pbLoadMore.gone()
                        showEmpty()
                    }
                    is Resource.Error -> {
                        binding.swipeMyAds.isRefreshing = false
                        binding.pbLoadMore.gone()
                        val errorMessage = state.errorMessage
                        if (errorMessage.lowercase().trim() == "unauthorized!"){
                            context?.startActivity<LoginActivity>()
                            (activity as MainActivity).finishAffinity()
                        }else{
                            showDialogError(requireContext(), errorMessage)
                        }
                    }
                    Resource.Loading -> {
                        if (isFirstPage){
                            binding.swipeMyAds.isRefreshing = true
                        }else{
                            binding.pbLoadMore.visible()
                        }
                    }
                    is Resource.Success -> {
                        binding.swipeMyAds.isRefreshing = false
                        binding.pbLoadMore.invisible()
                        hideEmpty()
                        isFirstPage = false

                        val data = state.data
                        val dataProducts = data.dataProduct
                        myAdsResponse = data
                        if (dataProducts != null){
                            // myAdsAdapter.insertData(dataProducts)
                        }
                    }
                }
            }
    }

    private fun hideEmpty() {
        binding.ivEmpty.gone()
        binding.rvMyAds.visible()
    }

    private fun showEmpty() {
        binding.ivEmpty.visible()
        binding.rvMyAds.gone()
    }

    private fun getToken() {
        val user = HawkStorage.instance(context).getUser()
        userId = user.id
        token = user.accessToken.toString()
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