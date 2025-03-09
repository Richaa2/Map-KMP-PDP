package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow


class GetSavedLocationsInfoUseCase  constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Resource<List<LocationInfo>>> {
        return locationRepository.getSavedLocationsInfo()
    }
}