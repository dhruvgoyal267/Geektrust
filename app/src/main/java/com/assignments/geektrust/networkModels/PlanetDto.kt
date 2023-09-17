package com.assignments.geektrust.networkModels

import com.google.gson.annotations.SerializedName

data class PlanetDto(
    @SerializedName("distance") val distance: Int?,
    @SerializedName("name") val name: String?
)