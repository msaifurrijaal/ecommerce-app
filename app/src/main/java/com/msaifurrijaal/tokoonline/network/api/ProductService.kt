package com.msaifurrijaal.tokoonline.network.api

import com.google.gson.reflect.TypeToken
import com.msaifurrijaal.tokoonline.data.model.product.CreateAdsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProductService {

    @Headers("Content-type: application/json")
    @POST("product")
    suspend fun createAds(
        @Header("authorization") token: String,
        @Body body: String
    ) : Response<CreateAdsResponse>
}