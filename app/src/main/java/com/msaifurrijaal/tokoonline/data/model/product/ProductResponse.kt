package com.msaifurrijaal.tokoonline.data.model.product


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("currentPage")
    val currentPage: Int? = null,
    @SerializedName("data")
    val dataProduct: List<DataProduct?>? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("totalItems")
    val totalItems: Int? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null
)