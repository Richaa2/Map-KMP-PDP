package com.richaa2.map.kmp.data.source

import com.richaa2.db.Camera_position
import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.data.source.local.model.LocalCameraPosition
import com.richaa2.map.kmp.data.source.local.model.LocalLocationInfo
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getLastCameraPosition(): Camera_position?
    fun insertCameraPosition(cameraPosition: LocalCameraPosition)
    fun isExistLocationById(id: Long): Boolean
    fun insertLocation(location: LocalLocationInfo)
    fun updateLocation(location: LocalLocationInfo)
    fun deleteLocationById(id: Long)
    fun getLocationById(id: Long): Locations_info?
    fun getAllLocations(): Flow<List<Locations_info>>
}