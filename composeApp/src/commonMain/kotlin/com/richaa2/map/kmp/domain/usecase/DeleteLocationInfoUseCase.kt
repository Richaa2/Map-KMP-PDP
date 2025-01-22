package com.richaa2.mappdp.domain.usecase

import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.repository.LocationRepository

class DeleteLocationInfoUseCase  constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Long): Resource<Unit> {
        return locationRepository.deleteLocationInfoById(id)
    }
}