package com.assignments.geektrust.networkModels


import com.google.gson.annotations.SerializedName

data class VehicleDto(
    @SerializedName("max_distance") val maxDistance: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("speed") val speed: Int?,
    @SerializedName("total_no") val totalNo: Int?
)