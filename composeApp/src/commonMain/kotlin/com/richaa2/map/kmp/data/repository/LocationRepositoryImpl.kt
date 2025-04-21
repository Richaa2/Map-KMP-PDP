package com.richaa2.map.kmp.data.repository

import com.richaa2.map.kmp.core.error.ErrorHandler
import com.richaa2.map.kmp.data.source.LocalSource
import com.richaa2.map.kmp.data.source.local.model.mapper.LocationMapper.fromDomainToLocal
import com.richaa2.map.kmp.data.source.local.model.mapper.LocationMapper.fromLocalToDomain
import com.richaa2.map.kmp.data.source.local.model.mapper.LocationMapper.fromSqldelightToLocal
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.repository.LocationRepository
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class LocationRepositoryImpl(
    private val localSource: LocalSource,
    private val errorHandler: ErrorHandler,
    private val locationTracker: LocationTracker,

    ) : LocationRepository {
    private var _currentLocation: Flow<LatLong>? = null

    override suspend fun getLocationInfoById(id: Long): Resource<LocationInfo?> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(
                    localSource.getLocationById(id)?.fromSqldelightToLocal()
                        ?.fromLocalToDomain()
                )
            } catch (e: Exception) {
                Resource.Error(errorHandler.getErrorMessage(e), e)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSavedLocationsInfo(): Flow<Resource<List<LocationInfo>>> {
        return localSource.getAllLocations()
            .flatMapLatest { entities ->
                flow {
                    emit(Resource.Loading)
                    val locations = entities.map { entity ->
                        entity.fromSqldelightToLocal().fromLocalToDomain()
                    }
                    emit(Resource.Success(locations))
                }
            }.catch { e ->
                Resource.Error(errorHandler.getErrorMessage(e), e)
            }
    }

    override suspend fun upsertLocation(locationInfo: LocationInfo): Resource<Unit> {
        return try {
            if (locationInfo.id.let { localSource.isExistLocationById(it) }) {
                localSource.updateLocation(locationInfo.fromDomainToLocal())
            } else {
                localSource.insertLocation(locationInfo.fromDomainToLocal())
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }

    override suspend fun deleteLocationInfoById(id: Long): Resource<Unit> {
        return try {
            localSource.deleteLocationById(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }

    override fun getCurrentLocation(): Flow<LatLong> {
        return _currentLocation ?: locationTracker.getLocationsFlow()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .map { location -> LatLong(location.latitude, location.longitude) }
            .also { _currentLocation = it }

    }

    override suspend fun startLocationUpdates() {
        locationTracker.startTracking()
    }

    override fun stopLocationUpdates() {
        locationTracker.stopTracking()
    }


}