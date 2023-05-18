package com.msaifurrijaal.tokoonline.presentation.resultproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.ads.AdsRepository

class ResultProductViewModel: ViewModel() {

    fun findAds(lat: Double, lon: Double, title: String) =
        AdsRepository.findAds(lat, lon, title).asLiveData()
}