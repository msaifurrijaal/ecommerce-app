package com.msaifurrijaal.tokoonline.presentation.myads

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codepolitan.pemasaranproduk.presentation.myads.MyAdsAdapter
import com.google.gson.Gson
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.data.hawkstorage.HawkStorage
import com.msaifurrijaal.tokoonline.data.model.Resource
import com.msaifurrijaal.tokoonline.data.model.product.DataProduct
import com.msaifurrijaal.tokoonline.data.model.product.ProductResponse
import com.msaifurrijaal.tokoonline.data.model.product.UpdateProductRequest
import com.msaifurrijaal.tokoonline.databinding.FragmentMyAdsBinding
import com.msaifurrijaal.tokoonline.presentation.login.LoginActivity
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.presentation.sell.SellActivity
import com.msaifurrijaal.tokoonline.presentation.uploadphoto.UploadPhotoActivity
import com.msaifurrijaal.tokoonline.utils.gone
import com.msaifurrijaal.tokoonline.utils.invisible
import com.msaifurrijaal.tokoonline.utils.showDialogError
import com.msaifurrijaal.tokoonline.utils.showDialogLoading
import com.msaifurrijaal.tokoonline.utils.showDialogNotification
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

    private val onScrollState = NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
        val childHeight = v.getChildAt(0).measuredHeight
        val height = v.measuredHeight
        val totalHeight = childHeight - height
        val isScroll = myAdsResponse?.currentPage!! < myAdsResponse?.totalPages!!
        if (scrollY == totalHeight && isScroll){
            getDataMyAds(false)
        }
    }

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
                            myAdsAdapter.insertData(dataProducts as List<DataProduct>)
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
        myAdsAdapter.onClick { view, dataProduct, position ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.menuInflater.inflate(R.menu.config_my_ads_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_edit -> {
                        requireContext().startActivity<SellActivity>(
                            SellActivity.EXTRA_IS_EDIT to true,
                            SellActivity.EXTRA_DATA_PRODUCT to dataProduct
                        )
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_manage_photos -> {
                        requireContext().startActivity<UploadPhotoActivity>(
                            UploadPhotoActivity.EXTRA_IS_EDIT to true,
                            UploadPhotoActivity.EXTRA_ADS to dataProduct
                        )
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_delete -> {
                        val id = dataProduct.id
                        deleteProductToServer(id, position)
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }

            popupMenu.show()
        }

        myAdsAdapter.onClickSold { dataProduct, position ->
            updateSoldToServer(dataProduct, position)
        }

        binding.swipeMyAds.setOnRefreshListener { getDataMyAds(true) }
    }

    private fun updateSoldToServer(dataProduct: DataProduct, position: Int) {
        val updateProductRequest = UpdateProductRequest(
            categoryId = dataProduct.categoryId,
            title = dataProduct.title,
            brand = dataProduct.brand,
            model = dataProduct.model,
            year = dataProduct.year,
            price = dataProduct.price,
            description = dataProduct.description,
            address = dataProduct.address,
            locationLat = dataProduct.locLatitude,
            locationLong = dataProduct.locLongitude,
            sold = true
        )

        val body = Gson().toJson(updateProductRequest)

        myAdsViewModel.updateProduct(token, body, dataProduct.id!!).observe(viewLifecycleOwner){ state->
            when(state){
                Resource.Empty -> {
                    dialogLoading.dismiss()
                    showDialogNotification(requireContext(), "EMPTY")
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    val errorMessage = state.errorMessage
                    showDialogError(requireContext(), errorMessage)
                }
                Resource.Loading -> dialogLoading.show()
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    myAdsAdapter.updateDataSold(position)
                }
            }
        }
    }

    private fun deleteProductToServer(id: Int?, position: Int) {
        myAdsViewModel.deleteProduct(token, id!!).observe(viewLifecycleOwner){ state ->
            when(state){
                Resource.Empty -> {
                    dialogLoading.dismiss()
                    showDialogNotification(requireContext(), "EMPTY")
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    val errorMessage = state.errorMessage
                    showDialogError(requireContext(), errorMessage)
                }
                Resource.Loading -> dialogLoading.show()
                is Resource.Success -> {
                    dialogLoading.dismiss()
                    myAdsAdapter.delete(position)
                }
            }
        }
    }

    private fun initMyAds() {
        binding.rvMyAds.adapter = myAdsAdapter
        binding.nestedMyAds.setOnScrollChangeListener(onScrollState)
    }
}