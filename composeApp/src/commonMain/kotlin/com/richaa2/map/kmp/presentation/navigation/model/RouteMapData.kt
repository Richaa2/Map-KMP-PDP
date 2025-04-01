package com.richaa2.map.kmp.presentation.navigation.model

import com.richaa2.map.kmp.domain.model.ComputeRoute
import dev.icerock.moko.geo.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class RouteMapData(
    val distanceMeters: Int,
    val routePolyline: List<RoutePositionData>
) {
    companion object {
        fun fromComputeRoute(computeRoute: ComputeRoute?): RouteMapData? {
            if (computeRoute == null) return null
            return RouteMapData(
                distanceMeters = computeRoute.distanceMeters,
                routePolyline = computeRoute.routePolyline.map {
                    RoutePositionData(
                        it.latitude,
                        it.longitude
                    )
                }
            )
        }

        fun toComputeRoute(routeMapData: RouteMapData?): ComputeRoute? {
            if (routeMapData == null) return null
            return ComputeRoute(
                distanceMeters = routeMapData.distanceMeters,
                routePolyline = routeMapData.routePolyline.map { LatLng(it.latitude, it.longitude) }
            )
        }
    }
}