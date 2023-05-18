package com.msaifurrijaal.tokoonline.data.repository.product

import com.msaifurrijaal.tokoonline.data.model.product.ProductResponse

class ProductChaceDataSource {

    private var productResponse: ProductResponse? = null
    private var myAdsResponse: ProductResponse? = null

    fun saveDataProduct(productResponse: ProductResponse){
        this.productResponse = productResponse
    }

    fun getDataProduct() = productResponse

    fun saveMyAds(myAdsResponse: ProductResponse){
        this.myAdsResponse = myAdsResponse
    }

    fun getDataMyAds() = myAdsResponse

}