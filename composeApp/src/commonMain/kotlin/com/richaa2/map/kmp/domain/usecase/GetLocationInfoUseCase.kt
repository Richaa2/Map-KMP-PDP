package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow


class GetLocationInfoUseCase  constructor(
    private val locationRepository: LocationRepository,
) {
    suspend operator fun invoke(locationId: Long): Resource<LocationInfo?> {
        return locationRepository.getLocationInfoById(locationId)
    }
}