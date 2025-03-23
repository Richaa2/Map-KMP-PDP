package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentLocationUseCase(
    private val repository: LocationRepository,
) {
    operator fun invoke(): Flow<LatLong?> {
        return repository.getCurrentLocation()
    }
}