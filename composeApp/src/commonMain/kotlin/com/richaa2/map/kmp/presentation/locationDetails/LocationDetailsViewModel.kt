package com.richaa2.map.kmp.presentation.locationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.GetLocationInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.user_position_not_found
import org.jetbrains.compose.resources.getString

class LocationDetailsViewModel constructor(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val deleteLocationInfoUseCase: DeleteLocationInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LocationDetailsState>(LocationDetailsState.Loading)
    val uiState: StateFlow<LocationDetailsState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _onNavigateBackAction = MutableSharedFlow<Boolean>()
    val onNavigateBackAction: SharedFlow<Boolean> = _onNavigateBackAction.asSharedFlow()

    private val _onNavigateToRouteAction = MutableSharedFlow<LatLong>()
    val onNavigateToRouteAction: SharedFlow<LatLong> = _onNavigateToRouteAction.asSharedFlow()

    fun loadLocationDetails(locationId: Long, userPosition: LatLong?) {
        viewModelScope.launch {
            try {
                getLocationInfoUseCase(locationId).let { result ->
                    when (result) {
                        is Resource.Error -> _errorState.value = result.message
                        is Resource.Loading -> _uiState.value = LocationDetailsState.Loading
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.value = LocationDetailsState.Success(
                                    location = result.data,
                                    userPosition = userPosition?.let {
                                        LatLong(
                                            latitude = it.latitude,
                                            longitude = it.longitude
                                        )
                                    }
                                )
                            } ?: run {
                                _uiState.value = LocationDetailsState.NotFound
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun deleteLocation(locationId: Long) {
        viewModelScope.launch {
            when (val deleteResult = deleteLocationInfoUseCase(locationId)) {
                is Resource.Error -> _errorState.value = deleteResult.message
                is Resource.Loading -> _uiState.value = LocationDetailsState.Loading
                is Resource.Success -> {
                    _onNavigateBackAction.emit(true)
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorState.value = null
    }

    fun getRouteToLocation() {
        viewModelScope.launch {
            if ((_uiState.value as? LocationDetailsState.Success)?.userPosition != null) {
                val destinationPosition = LatLong(
                    latitude = (_uiState.value as LocationDetailsState.Success).location.latitude,
                    longitude = (_uiState.value as LocationDetailsState.Success).location.longitude
                )
                _onNavigateToRouteAction.emit(destinationPosition)

            } else {
                _errorState.value = getString(Res.string.user_position_not_found)
            }
        }
    }

    sealed class LocationDetailsState {
        data object Loading : LocationDetailsState()
        data class Success(val location: LocationInfo, val userPosition: LatLong?) :
            LocationDetailsState()

        data object NotFound : LocationDetailsState()
    }
}