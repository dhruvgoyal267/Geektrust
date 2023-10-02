package com.assignments.geektrust.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignments.geektrust.models.AppScreens
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.ui.composables.FinalResultScreen
import com.assignments.geektrust.ui.composables.HomeScreenComposable
import com.assignments.geektrust.ui.composables.PlanetAndVehicleSelectorComposable
import com.assignments.geektrust.ui.theme.GeekTrustTheme
import com.assignments.geektrust.viewmodel.FalconViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeekTrustTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel: FalconViewModel by viewModels()
                    val navController = rememberNavController()

                    val selectedPlanets = remember {
                        mutableStateListOf<Planet>()
                    }

                    val selectedVehicles = remember {
                        mutableStateListOf<Vehicle>()
                    }

                    val currentSelectionCount = remember {
                        mutableIntStateOf(0)
                    }

                    NavHost(
                        navController = navController,
                        startDestination = AppScreens.HOME.name
                    ) {
                        composable(AppScreens.HOME.name) {
                            HomeScreenComposable {
                                currentSelectionCount.intValue = 1
                                navController.navigate(
                                    AppScreens.PLANET_AND_VEHICLE_SELECTION.name
                                )
                            }
                        }

                        composable(AppScreens.PLANET_AND_VEHICLE_SELECTION.name) {

                            val currentSelectedVehicle: MutableState<Vehicle?> = remember {
                                mutableStateOf(null)
                            }
                            val currentSelectedPlanet: MutableState<Planet?> = remember {
                                mutableStateOf(null)
                            }


                            LaunchedEffect(key1 = currentSelectionCount, block = {
                                if (currentSelectionCount.intValue == 1) {
                                    currentSelectedPlanet.value = null
                                    currentSelectedVehicle.value = null
                                    viewModel.fetchPlanets()
                                    viewModel.fetchVehicles()
                                }
                            })

                            PlanetAndVehicleSelectorComposable(
                                currentSelectionCount.intValue,
                                planets = viewModel.planets.collectAsState().value,
                                vehicles = viewModel.vehicles.collectAsState().value,
                                selectedPlanet = currentSelectedPlanet.value,
                                selectedVehicle = currentSelectedVehicle.value,
                                onNextClicked = { selectedPlanet, selectedVehicle ->
                                    selectedPlanets.add(selectedPlanet)
                                    selectedVehicles.add(selectedVehicle)

                                    if (currentSelectionCount.intValue < 4) {
                                        currentSelectionCount.intValue += 1
                                        currentSelectedPlanet.value = null
                                        currentSelectedVehicle.value = null
                                        navController.navigate(AppScreens.PLANET_AND_VEHICLE_SELECTION.name)
                                    } else {
                                        navController.navigate(AppScreens.FINAL_RESULT.name)
                                    }
                                }
                            )
                        }

                        composable(AppScreens.FINAL_RESULT.name) {
                            LaunchedEffect(key1 = true) {
                                viewModel.findFalcon(
                                    selectedPlanets.toList(),
                                    selectedVehicles.toList()
                                )
                            }
                            FinalResultScreen(findFalconStatus = viewModel.findFalconStatus.collectAsState().value) {
                                navController.popBackStack()
                                currentSelectionCount.intValue = 0
                                navController.navigate(AppScreens.HOME.name)
                            }
                        }
                    }
                }
            }
        }
    }
}
