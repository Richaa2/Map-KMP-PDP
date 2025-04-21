package com.richaa2.map.kmp.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    val routes: List<RouteData>
)

@Serializable
data class RouteData(
    val distanceMeters: Int,
    val polyline: RoutePolyline
)

@Serializable
data class RoutePolyline(
    val encodedPolyline: String
)