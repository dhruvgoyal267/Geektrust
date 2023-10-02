package com.assignments.geektrust.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignments.geektrust.models.ErrorTypes
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
        MutableStateFlow(FindFalconStatus.NotFinding)
    val findFalconStatus: StateFlow<FindFalconStatus> = _findFalconStatus


    fun fetchPlanets() {
        viewModelScope.launch {
            try {
                val planetsList = falconRepository.getPlanets()
                _planets.emit(planetsList)
            } catch (e: Exception) {
                _planets.emit(emptyList())
            }
        }
    }

    fun fetchVehicles() {
        viewModelScope.launch {
            try {
                val vehiclesList = falconRepository.getVehicles()
                _vehicles.emit(vehiclesList)
            } catch (e: Exception) {
                _vehicles.emit(emptyList())
            }
        }
    }

    fun findFalcon(planets: List<Planet>, vehicle: List<Vehicle>) {
        viewModelScope.launch {
            try {
                _findFalconStatus.emit(FindFalconStatus.Finding)
                val findFalconStatus = falconRepository.findFalcon(planets, vehicle)
                _findFalconStatus.emit(findFalconStatus)
            } catch (e: Exception) {
                _findFalconStatus.emit(FindFalconStatus.Error(ErrorTypes.OtherError("Some error occurred, please try again!")))
            }
        }
    }
}