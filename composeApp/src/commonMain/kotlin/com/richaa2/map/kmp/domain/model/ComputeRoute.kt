package com.richaa2.map.kmp.domain.model

import dev.icerock.moko.geo.LatLng

data class ComputeRoute(
    val distanceMeters: Int,
    val routePolyline: List<LatLng>
)
