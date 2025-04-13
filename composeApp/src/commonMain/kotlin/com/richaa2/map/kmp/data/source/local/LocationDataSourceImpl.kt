package com.richaa2.map.kmp.data.source.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.richaa2.db.AppDatabase
import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.core.base64.byteArrayToBase64
import com.richaa2.map.kmp.data.source.local.model.LocalLocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class LocationDataSourceImpl(db: AppDatabase) {
    private val queries = db.locationInfoQueries
    fun isExistLocationById(id: Long): Boolean {
        return queries.isExist(id).executeAsOneOrNull() == true
    }

    fun insertLocation(location: LocalLocationInfo) {
            queries.insertLocation(
                latitude = location.latitude,
                longitude = location.longitude,
                title = location.title,
                description = location.description,
                imageUrl = location.imageUrl?.byteArrayToBase64()
            )
    }

    fun updateLocation(location: LocalLocationInfo) {
        location.id.let {
            queries.updateLocation(
                id = it,
                latitude = location.latitude,
                longitude = location.longitude,
                title = location.title,
                description = location.description,
                imageUrl = location.imageUrl?.byteArrayToBase64()
            )
        }
    }

    fun deleteLocationById(id: Long) {
        queries.deleteLocationById(id)
    }

    fun getLocationById(id: Long): Locations_info? {
        return queries.getLocationById(id).executeAsOneOrNull()
    }

    fun getAllLocations(): Flow<List<Locations_info>> {
        return queries.getAllLocations().asFlow().mapToList(Dispatchers.IO)
    }

}