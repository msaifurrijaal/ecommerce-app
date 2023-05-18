package com.msaifurrijaal.tokoonline.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.product.ProductRepository

class HomeViewModel: ViewModel() {

    fun getDataProducts(token:String, isSwipe: Boolean) =
        ProductRepository.showProduct(token, isSwipe).asLiveData()
}