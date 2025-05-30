package com.richaa2.map.kmp.presentation.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.locationInfo.GetSavedLocationsInfoUseCase
import com.richaa2.map.kmp.domain.usecase.route.GetRoutesUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.StartLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.StopLocationUpdatesUseCase
import dev.icerock.moko.geo.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.location_disabled_message
import org.jetbrains.compose.resources.getString

class MapViewModel(
    private val getSavedLocationsInfoUseCase: GetSavedLocationsInfoUseCase,
    private val stopLocationUpdatesUseCase: StopLocationUpdatesUseCase,
    private val startLocationUpdatesUseCase: StartLocationUpdatesUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _errorAction = MutableSharedFlow<MapErrorAction>(replay = 0)
    val errorAction: SharedFlow<MapErrorAction> = _errorAction.asSharedFlow()


    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }

    fun startLocationUpdates() {
        viewModelScope.launch {
            startLocationUpdatesUseCase()
        }
    }

    private fun stopLocationUpdates() {
        stopLocationUpdatesUseCase()
    }

    fun loadSavedLocations() {
        viewModelScope.launch {
            getSavedLocationsInfoUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val locations = result.data
                        _uiState.value = MapUiState.MapSuccess(locations)
                    }

                    is Resource.Error -> {
                        _errorAction.emit(MapErrorAction.LocationError(result.message))
                    }

                    Resource.Loading -> {
                        _uiState.value = MapUiState.Loading
                    }
                }
            }
        }
    }

    fun onLocationDisabledMessage() {
        viewModelScope.launch {
            _errorAction.emit(MapErrorAction.LocationDisabled(getString(Res.string.location_disabled_message)))
        }
    }

    fun getRoute(origin: LatLong, destination: LatLong) {
        viewModelScope.launch {
            val routeData = getRoutesUseCase(
                origin = origin,
                destination = destination
            )
            _uiState.value = MapUiState.Loading
            when (routeData) {
                is Resource.Error -> {
                    _errorAction.emit(MapErrorAction.RouteError(routeData.message))
                }

                is Resource.Loading -> _uiState.value = MapUiState.Loading
                is Resource.Success -> _uiState.value = MapUiState.RouteSuccess(
                    routes = routeData.data.first().routePolyline,
                    distance = routeData.data.first().distanceMeters
                )

            }
        }
    }

    sealed class MapUiState {
        data object Loading : MapUiState()
        data class MapSuccess(val locations: List<LocationInfo>) : MapUiState()
        data class RouteSuccess(val routes: List<LatLng>, val distance: Int) : MapUiState()
    }

    sealed class MapErrorAction(val snackbarMassage: String) {
        data class LocationDisabled(val message: String) : MapErrorAction(message)
        data class LocationError(val message: String) : MapErrorAction(message)
        data class RouteError(val message: String) : MapErrorAction(message)
    }

}