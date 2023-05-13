package com.msaifurrijaal.tokoonline.presentation.sell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.product.ProductRepository

class SellViewModel: ViewModel() {

    fun createAds(token: String, body: String) =
        ProductRepository.createAds(token, body).asLiveData()
}