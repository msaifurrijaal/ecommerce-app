package com.msaifurrijaal.tokoonline.data.model.auth


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String
)