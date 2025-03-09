package com.richaa2.map.kmp.domain.usecase

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.repository.LocationRepository

class DeleteLocationInfoUseCase  constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Long): Resource<Unit> {
        return locationRepository.deleteLocationInfoById(id)
    }
}