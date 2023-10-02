package com.assignments.geektrust.models

import com.assignments.geektrust.networkModels.PlanetDto
import com.assignments.geektrust.utils.bothNotNull

data class Planet(
    val distance: Int,
    val name: String
) : DataListItem(name)

fun PlanetDto.toPlanet(): Planet? =
    bothNotNull(name, distance)?.let { (name, distance) ->
        Planet(name = name, distance = distance)
    }