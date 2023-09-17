package com.assignments.geektrust.networkModels

import com.google.gson.annotations.SerializedName

data class FindFalconResponse(
    @SerializedName("planet_name") val planetName: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("error") val error: String? = null
)
