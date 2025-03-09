package com.richaa2.map.kmp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.GetCurrentLocationUseCase
import com.richaa2.map.kmp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.map.kmp.domain.usecase.StartLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.StopLocationUpdatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapViewModel constructor(
    private val getSavedLocationsInfoUseCase: GetSavedLocationsInfoUseCase,
    private val stopLocationUpdatesUseCase: StopLocationUpdatesUseCase,
    private val startLocationUpdatesUseCase: StartLocationUpdatesUseCase,
    getCurrentLocationUseCase: GetCurrentLocationUseCase,
//    private val resourceProvider: ResourceProvider,
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


    init {
        loadSavedLocations()
        getSavedLocationsInfoUseCase()
        viewModelScope.launch {
            startLocationUpdatesUseCase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopLocationUpdates()
    }


    private fun stopLocationUpdates() {
        stopLocationUpdatesUseCase()
    }

    private fun loadSavedLocations() {
        viewModelScope.launch {
            getSavedLocationsInfoUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val locations = result.data
                        _uiState.value = MapUiState.Success(locations)
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
//            _errorState.value =
//                resourceProvider.getString(R.string.location_disabled_message)
        }
    }

    sealed class MapUiState {
        object Loading : MapUiState()
        data class Success(val locations: List<LocationInfo>) : MapUiState()
    }


}