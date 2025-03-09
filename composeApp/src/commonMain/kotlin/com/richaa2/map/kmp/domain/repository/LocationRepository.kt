package com.richaa2.map.kmp.domain.repository

import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    suspend fun getLocationInfoById(id: Long): Resource<LocationInfo?>
    fun getSavedLocationsInfo(): Flow<Resource<List<LocationInfo>>>
    suspend fun upsertLocation(locationInfo: LocationInfo): Resource<Unit>
    suspend fun deleteLocationInfoById(id: Long): Resource<Unit>
    fun getCurrentLocation(): Flow<LatLong>
    fun stopLocationUpdates()
    suspend fun startLocationUpdates()
}