package com.msaifurrijaal.tokoonline.data.repository.product

import com.msaifurrijaal.tokoonline.data.model.ApiResponse
import com.msaifurrijaal.tokoonline.data.model.Resource
import com.msaifurrijaal.tokoonline.data.model.product.CreateAdsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

object ProductRepository {

    private val productRemoteDataSource = ProductRemoteDataSource()

    fun createAds(token: String, body: String) : Flow<Resource<CreateAdsResponse>> = flow {
        emit(Resource.Loading)

        when(val apiResponse = productRemoteDataSource.createAds(token, body).first()) {
            is ApiResponse.Empty -> {
                emit(Resource.Empty)
            }
            is ApiResponse.Error -> {
                val errorMessage = apiResponse.errorMessage
                emit(Resource.Error(errorMessage))
            }
            is ApiResponse.Success -> {
                val data = apiResponse.data
                emit(Resource.Success(data))
            }
        }
    }
}