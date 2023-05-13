package com.msaifurrijaal.tokoonline.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.auth.AuthRepository

class RegisterViewModel: ViewModel() {

    fun register(body: String) =
        AuthRepository.register(body).asLiveData()
}