package com.richaa2.map.kmp.presentation.locationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
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

class LocationDetailsViewModel  constructor(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val deleteLocationInfoUseCase: DeleteLocationInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<LocationDetailsState>(LocationDetailsState.Loading)
    val uiState: StateFlow<LocationDetailsState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    private val _onNavigateBackAction = MutableSharedFlow<Boolean>()
    val onNavigateBackAction: SharedFlow<Boolean> = _onNavigateBackAction.asSharedFlow()

    fun loadLocationDetails(locationId: Long) {
        viewModelScope.launch {
            try {
                getLocationInfoUseCase(locationId).let { result ->
                    when (result) {
                        is Resource.Error -> _errorState.value = result.message
                        is Resource.Loading -> _uiState.value = LocationDetailsState.Loading
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.value = LocationDetailsState.Success(result.data)
                            } ?: run {
                                _uiState.value = LocationDetailsState.NotFound
                            }
                        }
                    }
                }
            } catch (e: Exception) {
//                _errorState.value = e.localizedMessage
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

    sealed class LocationDetailsState {
        data object Loading : LocationDetailsState()
        data class Success(val location: LocationInfo) : LocationDetailsState()
        data object NotFound : LocationDetailsState()
    }
}