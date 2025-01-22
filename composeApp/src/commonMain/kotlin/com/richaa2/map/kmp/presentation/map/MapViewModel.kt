package com.richaa2.map.kmp.dependecies

import androidx.compose.ui.input.key.Key.Companion.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.mappdp.core.common.ResourceProvider
import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel  constructor(
    private val getSavedLocationsInfoUseCase: GetSavedLocationsInfoUseCase,
//    private val fusedLocationProviderClient: FusedLocationProviderClient,
//    private val resourceProvider: ResourceProvider,
) : ViewModel() {

//    private val _currentLocation = MutableStateFlow<Location?>(null)
//    val currentLocation: StateFlow<Location?> = _currentLocation

    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()


    init {
        loadSavedLocations()
    }

    //    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
//        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
//            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
//            UPDATE_LOCATION_INTERVAL_MS
//        ).apply {
//            setMinUpdateIntervalMillis(MIN_UPDATE_LOCATION_INTERVAL_MS)
//        }.build()
//
//        fusedLocationProviderClient.requestLocationUpdates(
//            locationRequest,
//            object : com.google.android.gms.location.LocationCallback() {
//                override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
//                    val location = locationResult.lastLocation
//                    _currentLocation.value = location
//                }
//
//                override fun onLocationAvailability(p0: LocationAvailability) {
//                    super.onLocationAvailability(p0)
//                    if (!p0.isLocationAvailable) {
//                        _currentLocation.value = null
//                        onLocationDisabledMessage()
//                    }
//                }
//            },
//            null
//        )
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

                    is Resource.Loading -> {
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