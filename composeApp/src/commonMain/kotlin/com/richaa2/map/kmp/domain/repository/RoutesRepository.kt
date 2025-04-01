package com.richaa2.map.kmp.domain.repository

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.ComputeRoute
import com.richaa2.map.kmp.domain.model.LatLong

interface RoutesRepository {
    suspend fun getRoutes(origin: LatLong, destination: LatLong): Resource<List<ComputeRoute>>
}