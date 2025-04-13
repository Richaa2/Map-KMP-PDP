package com.richaa2.map.kmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.usecase.GetCurrentLocationUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : ViewModel() {

    val currentLocation: StateFlow<LatLong?> = getCurrentLocationUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    //TODO ADD HERE ERROR
}