package com.msaifurrijaal.tokoonline.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.auth.AuthRepository

class LoginViewModel: ViewModel() {

    fun login(body: String) =
        AuthRepository.login(body).asLiveData()

}