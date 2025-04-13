package com.richaa2.map.kmp.data.source

import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.domain.model.LocationInfo
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    fun isExistLocationById(id: Long): Boolean
    fun insertLocation(location: LocationInfo)
    fun updateLocation(location: LocationInfo)
    fun deleteLocationById(id: Long)
    fun getLocationById(id: Long): Locations_info?
    fun getAllLocations(): Flow<List<Locations_info>>
}