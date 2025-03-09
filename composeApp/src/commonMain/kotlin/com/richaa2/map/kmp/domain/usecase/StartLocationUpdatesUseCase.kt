package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.repository.LocationRepository

class StartLocationUpdatesUseCase(private val locationRepository: LocationRepository) {

    suspend operator fun invoke() {
        locationRepository.startLocationUpdates()
    }
}