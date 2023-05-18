package com.msaifurrijaal.tokoonline.data.repository.ads

import com.msaifurrijaal.tokoonline.data.model.product.ProductResponse

class AdsCacheDataSource {

    private var findAdsResponse: ProductResponse? = null

    fun getDataFindAds() = findAdsResponse

    fun saveDataFindAds(findAdsResponse: ProductResponse?){
        this.findAdsResponse = findAdsResponse
    }
}