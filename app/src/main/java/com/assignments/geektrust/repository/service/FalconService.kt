package com.assignments.geektrust.repository.service

import com.assignments.geektrust.networkModels.FindFalconResponse
import com.assignments.geektrust.networkModels.FindFalconRequest
import com.assignments.geektrust.networkModels.PlanetDto
import com.assignments.geektrust.networkModels.TokenDto
import com.assignments.geektrust.networkModels.VehicleDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FalconService {
    @GET("planets")
    suspend fun getPlanets(): List<PlanetDto>

    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleDto>

    @POST("token")
    @Headers("Accept: application/json")
    suspend fun getToken(): TokenDto

    @POST("find")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun findFalcon(@Body findFalconRequest: FindFalconRequest): FindFalconResponse
}