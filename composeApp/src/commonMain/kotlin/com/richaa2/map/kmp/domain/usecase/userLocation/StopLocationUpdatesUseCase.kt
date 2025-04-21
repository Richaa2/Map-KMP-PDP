package com.richaa2.map.kmp.domain.usecase.userLocation

import com.richaa2.map.kmp.domain.repository.LocationRepository

class StopLocationUpdatesUseCase(private val locationRepository: LocationRepository) {

    operator fun invoke() {
        locationRepository.stopLocationUpdates()
    }
}