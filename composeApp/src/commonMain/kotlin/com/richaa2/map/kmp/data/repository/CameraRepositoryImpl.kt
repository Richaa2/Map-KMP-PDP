package com.richaa2.map.kmp.data.repository

import com.richaa2.map.kmp.data.source.LocalSource
import com.richaa2.map.kmp.data.source.local.model.mapper.CameraPositionMapper.fromDomainToLocal
import com.richaa2.map.kmp.data.source.local.model.mapper.CameraPositionMapper.fromLocalToDomain
import com.richaa2.map.kmp.data.source.local.model.mapper.CameraPositionMapper.fromSqldelightToLocal
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.repository.CameraRepository
import com.richaa2.map.kmp.presentation.screens.map.utils.DEFAULT_MAP_LATITUDE
import com.richaa2.map.kmp.presentation.screens.map.utils.DEFAULT_MAP_LONGITUDE
import com.richaa2.map.kmp.presentation.screens.map.utils.DEFAULT_ZOOM_LEVEL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CameraRepositoryImpl(
    private val localSource: LocalSource,
) : CameraRepository {
    override suspend fun getLastCameraPosition(): CameraPosition {
        return withContext(Dispatchers.IO) {
            localSource.getLastCameraPosition()
                ?.fromSqldelightToLocal()
                ?.fromLocalToDomain()
                ?: DEFAULT_CAMERA_POSITION

        }
    }

    override suspend fun insertCameraPosition(cameraPosition: CameraPosition) {
        withContext(Dispatchers.IO) {
            localSource.insertCameraPosition(cameraPosition.fromDomainToLocal())
        }
    }

    companion object {
        private val DEFAULT_CAMERA_POSITION = CameraPosition(
            target = LatLong(latitude = DEFAULT_MAP_LATITUDE, longitude = DEFAULT_MAP_LONGITUDE),
            zoom = DEFAULT_ZOOM_LEVEL
        )
    }
}