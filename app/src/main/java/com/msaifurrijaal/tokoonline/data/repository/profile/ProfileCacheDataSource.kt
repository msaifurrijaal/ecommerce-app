package com.msaifurrijaal.tokoonline.data.repository.profile

import com.msaifurrijaal.tokoonline.data.model.profile.ProfileResponse

class ProfileCacheDataSource {
    private var profileResponse: ProfileResponse? = null

    fun getProfile() = profileResponse

    fun saveProfile(profileResponse: ProfileResponse){
        this.profileResponse = profileResponse
    }
}