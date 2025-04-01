package com.richaa2.map.kmp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.GetCurrentLocationUseCase
import com.richaa2.map.kmp.domain.usecase.GetRoutesUseCase
import com.richaa2.map.kmp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.map.kmp.domain.usecase.StartLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.StopLocationUpdatesUseCase
import dev.icerock.moko.geo.LatLng
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.location_disabled_message
import org.jetbrains.compose.resources.getString

class MapViewModel constructor(
    private val getSavedLocationsInfoUseCase: GetSavedLocationsInfoUseCase,
    private val stopLocationUpdatesUseCase: StopLocationUpdatesUseCase,
    private val startLocationUpdatesUseCase: StartLocationUpdatesUseCase,
    private val getRoutesUseCase: GetRoutesUseCase,
    getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : ViewModel() {

    val currentLocation: StateFlow<LatLong?> = getCurrentLocationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private var _onErrorRouteAction: MutableSharedFlow<String?> = MutableStateFlow(null)
    val onErrorRouteAction: SharedFlow<String?> = _onErrorRouteAction

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
                        _errorState.value = result.message
                    }

                    Resource.Loading -> {
                        _uiState.value = MapUiState.Loading
                    }
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorState.value = null
    }

    fun onLocationDisabledMessage() {
        viewModelScope.launch {
            getString(Res.string.location_disabled_message)
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
                    _errorState.value = routeData.message
                    _onErrorRouteAction.emit(routeData.message)
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


}