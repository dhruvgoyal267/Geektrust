package com.assignments.geektrust.repository

import com.assignments.geektrust.models.FindFalconStatus
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle

interface FalconRepository {

    suspend fun getPlanets(): List<Planet>

    suspend fun getVehicles(): List<Vehicle>

    suspend fun findFalcon(planets: List<Planet>, vehicle: List<Vehicle>): FindFalconStatus
}