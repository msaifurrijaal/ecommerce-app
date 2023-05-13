package com.msaifurrijaal.tokoonline.data.repository.product

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msaifurrijaal.tokoonline.data.model.ApiResponse
import com.msaifurrijaal.tokoonline.data.model.auth.LoginResponse
import com.msaifurrijaal.tokoonline.data.model.product.CreateAdsResponse
import com.msaifurrijaal.tokoonline.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

public class ProductRemoteDataSource {

    suspend fun createAds(token: String, body: String): Flow<ApiResponse<CreateAdsResponse>> = flow {
        try {
            val response = ApiService.productService().createAds(token, body)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } else {
                val type = object : TypeToken<CreateAdsResponse>(){}.type
                val errorResponse: CreateAdsResponse = Gson().fromJson(response.errorBody()?.charStream(), type)
                emit(ApiResponse.Error(errorResponse.message.toString()))
            }
        } catch (t: Throwable) {
            emit(ApiResponse.Error(t.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}