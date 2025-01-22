package com.richaa2.mappdp.domain.repository

import com.richaa2.mappdp.domain.common.Resource
import com.richaa2.mappdp.domain.model.LocationInfo
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    fun getLocationInfoById(id: Long): Flow<Resource<LocationInfo?>>
    fun getSavedLocationsInfo(): Flow<Resource<List<LocationInfo>>>
    suspend fun upsertLocation(locationInfo: LocationInfo): Resource<Unit>
    suspend fun deleteLocationInfoById(id: Long): Resource<Unit>
}