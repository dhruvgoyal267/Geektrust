package com.assignments.geektrust

import com.assignments.geektrust.models.FindFalconStatus
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.repository.FalconRepository
import com.assignments.geektrust.viewmodel.FalconViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FalconViewModelTest {

    private lateinit var falconRepository: FalconRepository
    private lateinit var falconViewModel: FalconViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        falconRepository = mockk()
        falconViewModel = FalconViewModel(falconRepository)
    }


    @Test
    fun `check fetch planets`() = runTest {
        val mockPlanetsResponse = listOf(
            Planet(100, "Planet1"),
            Planet(200, "Planet2")
        )

        coEvery {
            falconRepository.getPlanets()
        } returns mockPlanetsResponse

        falconViewModel.fetchPlanets()

        delay(100)

        val planets = falconViewModel.planets.value

        Assert.assertEquals(2, planets.size)

        Assert.assertEquals(100, mockPlanetsResponse[0].distance)
        Assert.assertEquals("Planet1", mockPlanetsResponse[0].name)

        Assert.assertEquals(200, mockPlanetsResponse[1].distance)
        Assert.assertEquals("Planet2", mockPlanetsResponse[1].name)
    }

    @Test
    fun `check vehicles fetch`() = runTest {
        val mockVehiclesResponse = listOf(
            Vehicle(100, "Vehicle1", 100, 2),
            Vehicle(200, "Vehicle2", 200, 3),
        )
        coEvery {
            falconRepository.getVehicles()
        } returns mockVehiclesResponse


        falconViewModel.fetchVehicles()

        delay(100)

        val response = falconViewModel.vehicles.value

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
    fun `find falcon status test`() = runTest {
        val planets = listOf(
            Planet(100, "Planet1"),
            Planet(200, "Planet2")
        )
        val vehicles = listOf(
            Vehicle(100, "Vehicle1", 100, 2),
            Vehicle(200, "Vehicle2", 200, 3),
        )

        val findFalconStatus = FindFalconStatus.FalconNotFound

        every { findFalconStatus } returns FindFalconStatus.FalconNotFound

        coEvery {
            falconRepository.findFalcon(any(), any())
        } returns findFalconStatus


        Assert.assertEquals(
            FindFalconStatus.FalconFinding,
            falconViewModel.findFalconStatus.value
        )

        falconViewModel.findFalcon(planets = planets, vehicle = vehicles)

        delay(100)

        Assert.assertEquals(
            findFalconStatus,
            falconViewModel.findFalconStatus.value
        )
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }
}