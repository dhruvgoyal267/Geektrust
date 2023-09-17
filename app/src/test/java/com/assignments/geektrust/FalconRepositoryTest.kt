package com.assignments.geektrust

import com.assignments.geektrust.models.ErrorTypes
import com.assignments.geektrust.models.FindFalconStatus
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.networkModels.FindFalconResponse
import com.assignments.geektrust.networkModels.PlanetDto
import com.assignments.geektrust.networkModels.TokenDto
import com.assignments.geektrust.networkModels.VehicleDto
import com.assignments.geektrust.repository.FalconRepository
import com.assignments.geektrust.repository.FalconRepositoryImpl
import com.assignments.geektrust.repository.service.FalconService
import com.assignments.geektrust.utils.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FalconRepositoryTest {

    private lateinit var falconService: FalconService
    private lateinit var falconRepository: FalconRepository

    @Before
    fun setup() {
        falconService = mockk()

        falconRepository = FalconRepositoryImpl(falconService, DispatcherProvider)
    }

    @Test
    fun testGetPlanets() = runTest {
        val mockPlanetsResponse = listOf(
            PlanetDto(100, "Planet1"),
            PlanetDto(200, "Planet2")
        )
        coEvery {
            falconService.getPlanets()
        } returns mockPlanetsResponse

        val response = falconRepository.getPlanets()
        Assert.assertEquals(2, response.size)

        Assert.assertEquals(100, mockPlanetsResponse[0].distance)
        Assert.assertEquals("Planet1", mockPlanetsResponse[0].name)

        Assert.assertEquals(200, mockPlanetsResponse[1].distance)
        Assert.assertEquals("Planet2", mockPlanetsResponse[1].name)
    }


    @Test
    fun testGetVehicles() = runTest {
        val mockVehiclesResponse = listOf(
            VehicleDto(100, "Vehicle1", 100, 2),
            VehicleDto(200, "Vehicle2", 200, 3),
        )
        coEvery {
            falconService.getVehicles()
        } returns mockVehiclesResponse

        val response = falconRepository.getVehicles()
        Assert.assertEquals(2, response.size)

        Assert.assertEquals(100, mockVehiclesResponse[0].maxDistance)
        Assert.assertEquals("Vehicle1", mockVehiclesResponse[0].name)
        Assert.assertEquals(100, mockVehiclesResponse[0].speed)
        Assert.assertEquals(2, mockVehiclesResponse[0].totalNo)

        Assert.assertEquals(200, mockVehiclesResponse[1].maxDistance)
        Assert.assertEquals("Vehicle2", mockVehiclesResponse[1].name)
        Assert.assertEquals(200, mockVehiclesResponse[1].speed)
        Assert.assertEquals(3, mockVehiclesResponse[1].totalNo)
    }

    @Test
    fun findFalconSuccessTest() = runTest {
        val planets = listOf(
            Planet(100, "Planet1"),
            Planet(200, "Planet2")
        )
        val vehicles = listOf(
            Vehicle(100, "Vehicle1", 100, 2),
            Vehicle(200, "Vehicle2", 200, 3),
        )

        val findFalconMockResponse =
            FindFalconResponse(planetName = "Planet1", status = "success")

        coEvery {
            falconService.findFalcon(any())
        } returns findFalconMockResponse

        coEvery {
            falconService.getToken()
        } returns TokenDto("test token")

        val falconResponse = falconRepository.findFalcon(planets = planets, vehicle = vehicles)

        Assert.assertNotNull(falconResponse)

        Assert.assertTrue(falconResponse is FindFalconStatus.FalconFound)
        Assert.assertEquals("Planet1", (falconResponse as FindFalconStatus.FalconFound).planet)
    }


    @Test
    fun findFalconErrorTest() = runTest {
        val planets = listOf(
            Planet(100, "Planet1"),
            Planet(200, "Planet2")
        )
        val vehicles = listOf(
            Vehicle(100, "Vehicle1", 100, 2),
            Vehicle(200, "Vehicle2", 200, 3),
        )

        val findFalconMockResponse =
            FindFalconResponse(planetName = "Planet1", status = "success")

        coEvery {
            falconService.findFalcon(any())
        } returns findFalconMockResponse

        coEvery {
            falconService.getToken()
        } returns TokenDto()

        val falconResponse = falconRepository.findFalcon(planets = planets, vehicle = vehicles)

        Assert.assertNotNull(falconResponse)

        Assert.assertTrue(falconResponse is FindFalconStatus.Error)
        Assert.assertEquals(
            ErrorTypes.TokenNotFound,
            (falconResponse as FindFalconStatus.Error).errorType
        )
    }


    @Test
    fun findFalconNotFoundTest() = runTest {
        val planets = listOf(
            Planet(100, "Planet1"),
            Planet(200, "Planet2")
        )
        val vehicles = listOf(
            Vehicle(100, "Vehicle1", 100, 2),
            Vehicle(200, "Vehicle2", 200, 3),
        )

        val findFalconMockResponse =
            FindFalconResponse(status = "false")

        coEvery {
            falconService.findFalcon(any())
        } returns findFalconMockResponse

        coEvery {
            falconService.getToken()
        } returns TokenDto("Test token")

        val falconResponse = falconRepository.findFalcon(planets = planets, vehicle = vehicles)

        Assert.assertNotNull(falconResponse)

        Assert.assertTrue(falconResponse is FindFalconStatus.FalconNotFound)
    }
}