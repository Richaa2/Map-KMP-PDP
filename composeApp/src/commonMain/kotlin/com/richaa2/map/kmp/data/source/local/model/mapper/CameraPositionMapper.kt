package com.richaa2.map.kmp.data.source.local.model.mapper

import com.richaa2.db.Camera_position
import com.richaa2.map.kmp.data.source.local.model.LocalCameraPosition
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong

object CameraPositionMapper {

    fun Camera_position.fromSqldelightToLocal(): LocalCameraPosition {
        return LocalCameraPosition(
            latitude = this.latitude,
            longitude = this.longitude,
            zoom = this.zoom.toFloat()
        )
    }


    fun LocalCameraPosition.fromLocalToDomain(): CameraPosition {
        return CameraPosition(
            target = LatLong(
                latitude = this.latitude,
                longitude = this.longitude
            ),
            zoom = zoom
        )
    }

    fun CameraPosition.fromDomainToLocal(): LocalCameraPosition {
        return LocalCameraPosition(
            latitude = this.target.latitude,
            longitude = this.target.longitude,
            zoom = this.zoom,
        )
    }
}