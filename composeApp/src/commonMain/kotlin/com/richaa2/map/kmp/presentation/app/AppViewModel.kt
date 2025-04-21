package com.richaa2.map.kmp.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.usecase.cameraPosition.GetCameraPositionUseCase
import com.richaa2.map.kmp.domain.usecase.cameraPosition.SaveCameraPositionUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val saveCameraPositionUseCase: SaveCameraPositionUseCase,
    private val getCameraPositionUseCase: GetCameraPositionUseCase,
) : ViewModel() {

    val currentLocation: StateFlow<LatLong?> = getCurrentLocationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun saveCameraPosition(latitude: Double, longitude: Double, zoom: Float) {
        viewModelScope.launch {
            saveCameraPositionUseCase(
                latLong = LatLong(latitude = latitude, longitude = longitude),
                zoom = zoom
            )
        }
    }

    suspend fun getCameraPosition(): CameraPosition {
        return getCameraPositionUseCase()
    }

}