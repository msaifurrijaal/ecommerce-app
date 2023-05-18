package com.msaifurrijaal.tokoonline.presentation.uploadphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.msaifurrijaal.tokoonline.data.repository.product.ProductRepository
import okhttp3.MultipartBody

class UploadPhotoViewModel: ViewModel() {

    fun uploadImages(token: String, id: Int, images: List<MultipartBody.Part>) =
        ProductRepository.uploadImages(token, id, images).asLiveData()

    fun deleteImages(token: String, id: Int) =
        ProductRepository.deleteImage(token, id).asLiveData()
}