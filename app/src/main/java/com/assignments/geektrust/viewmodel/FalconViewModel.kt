package com.assignments.geektrust.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignments.geektrust.models.FindFalconStatus
import com.assignments.geektrust.models.Planet
import com.assignments.geektrust.models.Vehicle
import com.assignments.geektrust.repository.FalconRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FalconViewModel @Inject constructor(
    private val falconRepository: FalconRepository
) : ViewModel() {

    private val _planets: MutableStateFlow<List<Planet>> = MutableStateFlow(emptyList())
    val planets: StateFlow<List<Planet>> = _planets

    private val _vehicles: MutableStateFlow<List<Vehicle>> = MutableStateFlow(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    private val _findFalconStatus: MutableStateFlow<FindFalconStatus> =
        MutableStateFlow(FindFalconStatus.FalconFinding)
    val findFalconStatus: StateFlow<FindFalconStatus> = _findFalconStatus


    fun fetchPlanets() {
        viewModelScope.launch {
            val planetsList = falconRepository.getPlanets()
            _planets.emit(planetsList)
        }
    }

    fun fetchVehicles() {
        viewModelScope.launch {
            val vehiclesList = falconRepository.getVehicles()
            _vehicles.emit(vehiclesList)
        }
    }

    fun findFalcon(planets: List<Planet>, vehicle: List<Vehicle>) {
        viewModelScope.launch {
            val findFalconStatus = falconRepository.findFalcon(planets, vehicle)
            _findFalconStatus.emit(findFalconStatus)
        }
    }
}