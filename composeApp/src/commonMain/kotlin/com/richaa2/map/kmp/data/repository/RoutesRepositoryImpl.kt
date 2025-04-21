package com.richaa2.map.kmp.data.repository

import com.richaa2.map.kmp.core.error.ErrorHandler
import com.richaa2.map.kmp.data.source.RemoteSource
import com.richaa2.map.kmp.data.source.remote.model.mapper.RoutesMapper.fromDataToDomain
import com.richaa2.map.kmp.data.source.remote.model.mapper.RoutesMapper.fromDomainToData
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.ComputeRoute
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.repository.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RoutesRepositoryImpl(
    private val remoteSource: RemoteSource,
    private val errorHandler: ErrorHandler
) : RoutesRepository {
    override suspend fun getRoutes(
        origin: LatLong,
        destination: LatLong
    ): Resource<List<ComputeRoute>> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    remoteSource.computeRoute(
                        origin.fromDomainToData(),
                        destination.fromDomainToData()
                    ).routes.map { it.fromDataToDomain() }
                )
            } catch (e: Exception) {
                Resource.Error(errorHandler.getErrorMessage(e))
            }
        }
    }
}