package com.msaifurrijaal.tokoonline.network.api

import com.msaifurrijaal.tokoonline.data.model.profile.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileService {
    @GET("profile")
    suspend fun profile(
        @Header("authorization") token: String
    ): Response<ProfileResponse>
}