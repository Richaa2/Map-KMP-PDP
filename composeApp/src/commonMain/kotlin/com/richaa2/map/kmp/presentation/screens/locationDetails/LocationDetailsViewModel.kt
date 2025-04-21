package com.richaa2.map.kmp.presentation.screens.locationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.locationInfo.DeleteLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.GetLocationInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.error_unknown
import mapkmp.composeapp.generated.resources.user_position_not_found
import org.jetbrains.compose.resources.getString

class LocationDetailsViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val deleteLocationInfoUseCase: DeleteLocationInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LocationDetailsState>(LocationDetailsState.Loading)
    val uiState: StateFlow<LocationDetailsState> = _uiState.asStateFlow()

    private val _errorAction = MutableSharedFlow<String>(replay = 0)
    val errorAction: SharedFlow<String> = _errorAction.asSharedFlow()

    private val _onNavigateBackAction = MutableSharedFlow<Boolean>(replay = 0)
    val onNavigateBackAction: SharedFlow<Boolean> = _onNavigateBackAction.asSharedFlow()

    private val _onNavigateToRouteAction = MutableSharedFlow<LatLong>(replay = 0)
    val onNavigateToRouteAction: SharedFlow<LatLong> = _onNavigateToRouteAction.asSharedFlow()

    fun loadLocationDetails(locationId: Long, userPosition: LatLong?) {
        viewModelScope.launch {
            try {
                getLocationInfoUseCase(locationId).let { result ->
                    when (result) {
                        is Resource.Error -> _errorAction.emit(result.message)
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
                _errorAction.emit(e.message ?: getString(Res.string.error_unknown))
            }
        }
    }

    fun deleteLocation(locationId: Long) {
        viewModelScope.launch {
            when (val deleteResult = deleteLocationInfoUseCase(locationId)) {
                is Resource.Error -> _errorAction.emit(deleteResult.message)
                is Resource.Loading -> _uiState.value = LocationDetailsState.Loading
                is Resource.Success -> { _onNavigateBackAction.emit(true) }
            }
        }
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
                _errorAction.emit(getString(Res.string.user_position_not_found))
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