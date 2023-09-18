package com.assignments.geektrust.models

import com.assignments.geektrust.networkModels.VehicleDto
import com.assignments.geektrust.utils.quadNotNull

data class Vehicle(
    val maxDistance: Int,
    val name: String,
    val speed: Int,
    val totalNo: Int
){
    override fun toString(): String {
        return "$maxDistance-$name-$speed-$totalNo"
    }
}

fun VehicleDto.toVehicle(): Vehicle? = quadNotNull(
    maxDistance,
    name,
    speed,
    totalNo
)?.let { (maxD, name, speed, totalNo) ->
    Vehicle(
        maxDistance = maxD,
        name = name,
        speed = speed,
        totalNo = totalNo
    )
}