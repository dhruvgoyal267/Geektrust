package com.assignments.geektrust.utils

import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle

fun <A, B> bothNotNull(paramA: A?, paramB: B?): Pair<A, B>? {
    return if (paramA != null && paramB != null) {
        Pair(paramA, paramB)
    } else {
        null
    }
}

data class QuadDuple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

fun <A, B, C, D> quadNotNull(
    paramA: A?,
    paramB: B?,
    paramC: C?,
    paramD: D?
): QuadDuple<A, B, C, D>? {
    return if (paramA != null && paramB != null && paramC != null && paramD != null) {
        QuadDuple(paramA, paramB, paramC, paramD)
    } else {
        null
    }
}

fun String.getPlanet(): Planet? {
    return try {
        val values = split("-")
        Planet(values[0].toInt(), values[1])
    } catch (e: Exception) {
        null
    }
}

fun String.getVehicle(): Vehicle? {
    return try {
        val values = split("-")
        Vehicle(values[0].toInt(), values[1], values[2].toInt(), values[3].toInt())
    } catch (e: Exception) {
        null
    }

}