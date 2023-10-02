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
