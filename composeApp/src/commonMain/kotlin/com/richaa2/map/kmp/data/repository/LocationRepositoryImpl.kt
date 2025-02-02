package com.richaa2.mappdp.data.repository

import com.richaa2.mappdp.core.common.ErrorHandler
import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.map.kmp.data.source.local.LocationDao
import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.model.LocationInfo
import com.richaa2.mappdp.domain.repository.LocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class LocationRepositoryImpl  constructor(
    private val locationDao: LocationDao,
    private val mapper: LocationMapper,
    private val errorHandler: ErrorHandler,
) : LocationRepository {

    override fun getLocationInfoById(id: Long): Flow<Resource<LocationInfo?>> {
        return flow {
            emit(Resource.Loading)
            val entity = locationDao.getLocationById(id)
            val locationInfo = entity?.let { mapper.fromEntityToDomain(entity) }
            emit(Resource.Success(locationInfo))
        }.catch { e ->
            emit(Resource.Error(errorHandler.getErrorMessage(e), e))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSavedLocationsInfo(): Flow<Resource<List<LocationInfo>>> {
        return flow {  }
//        return locationDao.getAllLocations()
//            .flatMapLatest { entities ->
//                flow {
//                    emit(Resource.Loading)
//                    val locations = entities.map { entity -> mapper.fromEntityToDomain(entity) }
//                    emit(Resource.Success(locations))
//                }
//            }
//            .catch { e ->
//                emit(Resource.Error(errorHandler.getErrorMessage(e), e))
//            }
    }

    override suspend fun upsertLocation(locationInfo: LocationInfo): Resource<Unit> {
        return try {
            val entity = mapper.fromDomainToEntity(locationInfo)
            locationDao.upsertLocation(entity)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }

    override suspend fun deleteLocationInfoById(id: Long): Resource<Unit> {
        return try {
            locationDao.deleteLocationById(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }
}