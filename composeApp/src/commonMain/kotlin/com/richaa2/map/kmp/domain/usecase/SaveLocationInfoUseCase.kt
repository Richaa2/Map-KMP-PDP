package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.repository.LocationRepository


class SaveLocationInfoUseCase  constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(locationInfo: LocationInfo): Resource<Unit> {
       return locationRepository.upsertLocation(locationInfo)
    }
}