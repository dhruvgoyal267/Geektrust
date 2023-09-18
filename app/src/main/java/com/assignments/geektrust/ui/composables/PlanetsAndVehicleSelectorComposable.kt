package com.assignments.geektrust.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.utils.bothNotNull

@Composable
fun PlanetAndVehicleSelectorComposable(
    planets: List<Planet>,
    vehicles: List<Vehicle>,
    onNextClicked: (selectedPlanet: Planet, selectedVehicle: Vehicle) -> Unit
) {

    val currentSelectedVehicle: MutableState<Vehicle?> = remember {
        mutableStateOf(null)
    }
    val currentSelectedPlanet: MutableState<Planet?> = remember {
        mutableStateOf(null)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CustomDropDownMenu(menuItems = planets, menuItemsTitles = planets.map { it.name }) {
            currentSelectedPlanet.value = it
        }

        Spacer(modifier = Modifier.height(12.dp))

        CustomDropDownMenu(menuItems = vehicles, menuItemsTitles = vehicles.map { it.name }) {
            currentSelectedVehicle.value = it
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            bothNotNull(
                currentSelectedPlanet.value,
                currentSelectedVehicle.value
            )?.let { (planet, vehicle) ->
                onNextClicked(planet, vehicle)
            }
        }) {
            Text(text = "Proceed")
        }
    }

}

@Composable
fun <T> CustomDropDownMenu(
    menuItems: List<T>,
    menuItemsTitles: List<String>,
    onItemSelected: (item: T) -> Unit
) {
    DropdownMenu(expanded = false, onDismissRequest = {
    }) {

        menuItems.forEachIndexed { index, item ->
            DropdownMenuItem(text = {
                Text(text = menuItemsTitles[index])
            }, onClick = {
                onItemSelected(item)
            })
        }
    }
}