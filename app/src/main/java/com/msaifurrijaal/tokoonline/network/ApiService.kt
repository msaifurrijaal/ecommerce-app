package com.msaifurrijaal.tokoonline.network

import com.msaifurrijaal.tokoonline.network.api.AdsService
import com.msaifurrijaal.tokoonline.network.api.AuthService
import com.msaifurrijaal.tokoonline.network.api.ProductService

object ApiService {

    fun getAuthService(): AuthService {
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(AuthService::class.java)
    }

    fun productService(): ProductService {
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(ProductService::class.java)
    }

    fun adsService(): AdsService {
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(AdsService::class.java)
    }


}