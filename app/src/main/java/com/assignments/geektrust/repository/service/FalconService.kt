package com.assignments.geektrust.repository.service

import com.assignments.geektrust.networkModels.FindFalconResponse
import com.assignments.geektrust.networkModels.FindFalconRequest
import com.assignments.geektrust.networkModels.PlanetDto
import com.assignments.geektrust.networkModels.TokenDto
import com.assignments.geektrust.networkModels.VehicleDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FalconService {
    @GET("planets")
    suspend fun getPlanets(): List<PlanetDto>

    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleDto>

    @POST("token")
    suspend fun getToken(): TokenDto

    @POST("find")
    suspend fun findFalcon(@Body findFalconRequest: FindFalconRequest): FindFalconResponse
}