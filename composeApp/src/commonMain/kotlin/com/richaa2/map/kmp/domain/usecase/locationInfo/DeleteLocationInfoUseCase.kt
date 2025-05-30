package com.richaa2.map.kmp.domain.usecase.locationInfo

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.repository.LocationRepository

class DeleteLocationInfoUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Long): Resource<Unit> {
        return locationRepository.deleteLocationInfoById(id)
    }
}