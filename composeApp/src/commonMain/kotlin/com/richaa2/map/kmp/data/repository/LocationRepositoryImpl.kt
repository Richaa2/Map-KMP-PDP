package com.richaa2.map.kmp.data.repository

import com.richaa2.map.kmp.core.common.ErrorHandler
import com.richaa2.map.kmp.data.mapper.LocationMapper.fromEntityToDomain
import com.richaa2.map.kmp.data.source.local.LocationDataSource
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val errorHandler: ErrorHandler,
    private val locationTracker: LocationTracker,

    ) : LocationRepository {
    private var _currentLocation: Flow<LatLong>? = null

    override suspend fun getLocationInfoById(id: Long): Resource<LocationInfo?> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(locationDataSource.getLocationById(id)?.fromEntityToDomain())
            } catch (e: Exception) {
                Resource.Error(errorHandler.getErrorMessage(e), e)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSavedLocationsInfo(): Flow<Resource<List<LocationInfo>>> {
        return locationDataSource.getAllLocations()
            .flatMapLatest { entities ->
                flow {
                    emit(Resource.Loading)
                    val locations = entities.map { entity -> entity.fromEntityToDomain() }
                    emit(Resource.Success(locations))
                }
            }.catch { e ->
                Resource.Error(errorHandler.getErrorMessage(e), e)
            }
    }

    override suspend fun upsertLocation(locationInfo: LocationInfo): Resource<Unit> {
        return try {
            println("${locationInfo.id.let { locationDataSource.isExistLocationById(it) }}")
            if (locationInfo.id.let { locationDataSource.isExistLocationById(it) }) {
                locationDataSource.updateLocation(locationInfo)
            } else {
                locationDataSource.insertLocation(locationInfo)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }

    override suspend fun deleteLocationInfoById(id: Long): Resource<Unit> {
        return try {
            locationDataSource.deleteLocationById(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(errorHandler.getErrorMessage(e), e)
        }
    }

    override fun getCurrentLocation(): Flow<LatLong> {
        return _currentLocation ?: locationTracker.getLocationsFlow()
            .distinctUntilChanged()
            .map { location -> LatLong(location.latitude, location.longitude) }
            .also { _currentLocation = it }

    }

    override suspend fun startLocationUpdates() {
        println("startLocationUpdates")
        locationTracker.startTracking()
    }

    override fun stopLocationUpdates() {
        locationTracker.stopTracking()
    }


}