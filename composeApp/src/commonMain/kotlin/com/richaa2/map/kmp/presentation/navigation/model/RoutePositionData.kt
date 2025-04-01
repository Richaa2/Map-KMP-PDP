package com.richaa2.map.kmp.presentation.navigation.model

import com.richaa2.map.kmp.domain.model.LatLong
import kotlinx.serialization.Serializable

@Serializable
data class RoutePositionData(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun fromLatLong(latLng: LatLong?): RoutePositionData? {
            if (latLng == null) return null
            return RoutePositionData(latLng.latitude, latLng.longitude)
        }

        fun toLatLong(routePositionData: RoutePositionData?): LatLong? {
            if (routePositionData == null) return null
            return LatLong(routePositionData.latitude, routePositionData.longitude)
        }
    }
}
