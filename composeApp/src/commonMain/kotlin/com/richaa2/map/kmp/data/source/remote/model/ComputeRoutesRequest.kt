package com.richaa2.map.kmp.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LatLngWrapper(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class LocationRequest(
    val latLng: LatLngWrapper
)

@Serializable
data class LocationRequestWrapper(
    val location: LocationRequest
)

@Serializable
data class ComputeRoutesRequest(
    val origin: LocationRequestWrapper,
    val destination: LocationRequestWrapper,
    val travelMode: String,
    val routingPreference: String,
    val computeAlternativeRoutes: Boolean,
    val languageCode: String,
    val units: String
) {
    companion object {
        fun getDefaultRouteRequest(
            origin: LatLngWrapper,
            destination: LatLngWrapper
        ): ComputeRoutesRequest {
            return ComputeRoutesRequest(
                origin = LocationRequestWrapper(LocationRequest(origin)),
                destination = LocationRequestWrapper(LocationRequest(destination)),
                travelMode = "DRIVE",
                routingPreference = "TRAFFIC_AWARE",
                computeAlternativeRoutes = false,
                languageCode = "en-US",
                units = "METRIC"
            )
        }
    }

}
