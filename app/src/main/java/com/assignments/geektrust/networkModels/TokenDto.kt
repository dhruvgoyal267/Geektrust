package com.assignments.geektrust.networkModels

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("token") val token: String? = null
)