package com.msaifurrijaal.tokoonline.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msaifurrijaal.tokoonline.data.model.product.DataProduct

class SplashViewModel: ViewModel() {

    private var _allUser = MutableLiveData<DataProduct>()
    private val allUser : LiveData<DataProduct>
        get() = _allUser

}