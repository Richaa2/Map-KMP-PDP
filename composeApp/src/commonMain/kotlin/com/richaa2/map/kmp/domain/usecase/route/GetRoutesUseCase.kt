package com.richaa2.map.kmp.domain.usecase.route

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.ComputeRoute
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.repository.RoutesRepository

class GetRoutesUseCase(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(
        origin: LatLong,
        destination: LatLong
    ): Resource<List<ComputeRoute>> {
        return routesRepository.getRoutes(
            origin = origin,
            destination = destination
        )
    }
}