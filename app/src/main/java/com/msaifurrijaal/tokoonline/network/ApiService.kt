package com.msaifurrijaal.tokoonline.network

import com.msaifurrijaal.tokoonline.network.api.AuthService

object ApiService {

    fun getAuthService(): AuthService {
        return RetrofitClient.newInstance()
            .getRetrofitInstance()
            .create(AuthService::class.java)
    }
}