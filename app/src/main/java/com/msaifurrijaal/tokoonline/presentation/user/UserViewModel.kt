package com.msaifurrijaal.tokoonline.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.profile.ProfileRepository

class UserViewModel: ViewModel() {
    fun getProfile(token: String) =
        ProfileRepository.getProfile(token).asLiveData()
}