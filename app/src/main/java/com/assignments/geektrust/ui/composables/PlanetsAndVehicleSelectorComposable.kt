package com.assignments.geektrust.ui.composables

import android.content.res.Resources.Theme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.assignments.geektrust.models.DataListItem
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.ui.theme.Pink40
import com.assignments.geektrust.ui.theme.Purple40
import com.assignments.geektrust.utils.bothNotNull

@Composable
fun PlanetAndVehicleSelectorComposable(
    screenCount: Int,
    planets: List<Planet>,
    vehicles: List<Vehicle>,
    selectedPlanet: Planet?,
    selectedVehicle: Vehicle?,
    onNextClicked: (selectedPlanet: Planet, selectedVehicle: Vehicle) -> Unit
) {

    val currentSelectedVehicle: MutableState<Vehicle?> = remember {
        mutableStateOf(selectedVehicle)
    }
    val currentSelectedPlanet: MutableState<Planet?> = remember {
        mutableStateOf(selectedPlanet)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (planets.isEmpty() || vehicles.isEmpty()) {
            CircularProgressIndicator()

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Fetching data.....")
        } else {
            Text(text = "Choose pair $screenCount", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            CustomSelector(planets, currentSelectedPlanet.value, "Choose Planets") { planet ->
                currentSelectedPlanet.value = planet
            }

            Spacer(modifier = Modifier.height(12.dp))

            CustomSelector(vehicles, currentSelectedVehicle.value, "Choose Vehicle") { vehicle ->
                currentSelectedVehicle.value = vehicle
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : DataListItem> CustomSelector(
    listItems: List<T>,
    selectedItem: T?,
    defaultLabel: String,
    onItemSelected: (item: T) -> Unit
) {
    var currentSelectedItem by remember {
        mutableStateOf(selectedItem)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.clickable {
            isExpanded = true
        },
        value = currentSelectedItem?.title ?: defaultLabel,
        onValueChange = {}, enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = if (currentSelectedItem != null) Purple40 else Color.Gray,
            disabledBorderColor = if (currentSelectedItem != null) Purple40 else Color.Gray
        )
    )

    AnimatedVisibility(visible = isExpanded) {
        Dialog(onDismissRequest = {
            isExpanded = false
        }) {
            LazyColumn(
                modifier = Modifier
                    .background(Pink40, shape = RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                items(listItems) { item ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(2.dp, Color.Black)
                            .padding(8.dp)
                            .clickable {
                                currentSelectedItem = item
                                onItemSelected(item)
                                isExpanded = false
                            }, text = item.title
                    )
                }
            }
        }
    }
}