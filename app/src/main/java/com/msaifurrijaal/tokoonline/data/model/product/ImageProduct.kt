package com.msaifurrijaal.tokoonline.data.model.product


import com.google.gson.annotations.SerializedName

data class ImageProduct(
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("file")
    val image: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("product_id")
    val productId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)