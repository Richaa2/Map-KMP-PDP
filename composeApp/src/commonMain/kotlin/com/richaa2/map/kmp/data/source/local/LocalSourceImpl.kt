package com.richaa2.map.kmp.data.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.richaa2.db.AppDatabase
import com.richaa2.db.Camera_position
import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.core.base64.byteArrayToBase64
import com.richaa2.map.kmp.data.source.LocalSource
import com.richaa2.map.kmp.data.source.local.model.LocalCameraPosition
import com.richaa2.map.kmp.data.source.local.model.LocalLocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class LocalSourceImpl(db: AppDatabase) : LocalSource {
    private val locationInfoQueries = db.locationInfoQueries
    private val cameraPositionQueries = db.cameraPositionQueries

    override fun getLastCameraPosition(): Camera_position? {

        return cameraPositionQueries.selectCameraPosition().executeAsOneOrNull()
    }

    override fun insertCameraPosition(cameraPosition: LocalCameraPosition) {
        cameraPositionQueries.insertOrReplaceCameraPosition(
            latitude = cameraPosition.latitude,
            longitude = cameraPosition.longitude,
            zoom = cameraPosition.zoom.toDouble()
        )
    }

    override fun isExistLocationById(id: Long): Boolean {
        return locationInfoQueries.isExist(id).executeAsOneOrNull() == true
    }

    override fun insertLocation(location: LocalLocationInfo) {
        locationInfoQueries.insertLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            title = location.title,
            description = location.description,
            imageUrl = location.imageUrl?.byteArrayToBase64()
        )
    }

    override fun updateLocation(location: LocalLocationInfo) {
        location.id.let {
            locationInfoQueries.updateLocation(
                id = it,
                latitude = location.latitude,
                longitude = location.longitude,
                title = location.title,
                description = location.description,
                imageUrl = location.imageUrl?.byteArrayToBase64()
            )
        }
    }

    override fun deleteLocationById(id: Long) {
        locationInfoQueries.deleteLocationById(id)
    }

    override fun getLocationById(id: Long): Locations_info? {
        return locationInfoQueries.getLocationById(id).executeAsOneOrNull()
    }

    override fun getAllLocations(): Flow<List<Locations_info>> {
        return locationInfoQueries.getAllLocations().asFlow().mapToList(Dispatchers.IO)
    }

}