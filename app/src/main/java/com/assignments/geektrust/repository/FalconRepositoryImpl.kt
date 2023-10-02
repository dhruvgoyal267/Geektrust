package com.assignments.geektrust.repository

import com.assignments.geektrust.models.ErrorTypes
import com.assignments.geektrust.models.FindFalconStatus
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.models.toPlanet
import com.assignments.geektrust.models.toVehicle
import com.assignments.geektrust.networkModels.FindFalconRequest
import com.assignments.geektrust.repository.service.FalconService
import com.assignments.geektrust.utils.Constants
import com.assignments.geektrust.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FalconRepositoryImpl @Inject constructor(
    private val falconService: FalconService,
    private val dispatcherProvider: DispatcherProvider
) : FalconRepository {

    override suspend fun getPlanets(): List<Planet> =
        withContext(dispatcherProvider.IO) {
            falconService.getPlanets().mapNotNull { planetDto ->
                planetDto.toPlanet()
            }
        }

    override suspend fun getVehicles(): List<Vehicle> =
        withContext(dispatcherProvider.IO) {
            falconService.getVehicles().mapNotNull { vehicleDto ->
                vehicleDto.toVehicle()
            }
        }


    override suspend fun findFalcon(
        planets: List<Planet>,
        vehicle: List<Vehicle>
    ): FindFalconStatus {
        return withContext(dispatcherProvider.IO) {
            falconService.getToken().token?.let { token ->
                val planetNames: List<String> = planets.map { it.name }
                val vehicleNames: List<String> = vehicle.map { it.name }

                val findFalconRequest =
                    FindFalconRequest(planets = planetNames, vehicles = vehicleNames, token = token)

                val findFalconResponse = falconService.findFalcon(findFalconRequest)

                when {
                    findFalconResponse.error != null -> {
                        FindFalconStatus.Error(ErrorTypes.OtherError(findFalconResponse.error))
                    }

                    findFalconResponse.status == Constants.SUCCESS_RESPONSE && findFalconResponse.planetName != null -> {
                        FindFalconStatus.Found(findFalconResponse.planetName)
                    }

                    else -> FindFalconStatus.NotFound
                }
            } ?: FindFalconStatus.Error(ErrorTypes.TokenNotFound)
        }
    }
}